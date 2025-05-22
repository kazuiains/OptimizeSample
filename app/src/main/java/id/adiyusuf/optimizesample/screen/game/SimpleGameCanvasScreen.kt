package id.adiyusuf.optimizesample.screen.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

// Data class untuk merepresentasikan objek lingkaran yang jatuh
data class Circle(var x: Float, var y: Float, val radius: Float, val color: Color)

// Data class untuk merepresentasikan objek dayung (paddle)
data class Paddle(var x: Float, val y: Float, val width: Float, val height: Float, val color: Color)

// Sealed class untuk mengelola status permainan
sealed class GameState {
    data object Ready : GameState() // Siap bermain
    data object Playing : GameState() // Sedang bermain
    data object GameOver : GameState() // Permainan berakhir
}

@Composable
fun SimpleGameCanvasScreen() {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    // Konversi dp ke piksel untuk perhitungan game
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

    // State untuk status permainan
    var gameState by remember { mutableStateOf<GameState>(GameState.Ready) }
    // State untuk skor pemain
    var score by remember { mutableIntStateOf(0) }
    // List mutable untuk menyimpan objek lingkaran yang sedang jatuh
    val circles = remember { mutableStateListOf<Circle>() }
    // State untuk objek dayung (paddle)
    val paddle = remember {
        mutableStateOf(
            Paddle(
                // Posisi awal dayung di tengah bawah layar
                x = screenWidthPx / 2 - with(density) { 100.dp.toPx() } / 2,
                y = screenHeightPx - with(density) { 100.dp.toPx() },
                width = with(density) { 100.dp.toPx() },
                height = with(density) { 20.dp.toPx() },
                color = Color.DarkGray
            )
        )
    }

    // TextMeasurer untuk mengukur dan menggambar teks di Canvas
    val textMeasurer = rememberTextMeasurer()

    // Game loop yang berjalan saat gameState adalah Playing
    LaunchedEffect(gameState) {
        if (gameState == GameState.Playing) {
            var lastFrameTime = withFrameNanos { it } // Waktu frame terakhir
            while (gameState == GameState.Playing) {
                withFrameNanos { frameTime ->
                    // Hitung delta waktu antar frame (dalam detik)
                    val delta = (frameTime - lastFrameTime) / 1_000_000_000f
                    lastFrameTime = frameTime

                    // Perbarui posisi lingkaran yang jatuh
                    val newCircles = circles.map { circle ->
                        circle.copy(y = circle.y + (with(density) { 150.dp.toPx() } * delta)) // Kecepatan jatuh
                    }.toMutableList()

                    // Iterasi untuk menghapus lingkaran yang terlewat dan memeriksa Game Over
                    val iterator = newCircles.iterator()
                    while (iterator.hasNext()) {
                        val circle = iterator.next()
                        // Jika lingkaran benar-benar melewati batas bawah layar
                        if (circle.y - circle.radius > screenHeightPx) {
                            gameState = GameState.GameOver // Set status Game Over
                            iterator.remove() // Hapus lingkaran yang terlewat
                        }
                    }

                    // Tambahkan lingkaran baru secara berkala
                    if (Random.nextFloat() < 0.03f) { // Probabilitas muncul lingkaran baru
                        newCircles.add(
                            Circle(
                                // Posisi X acak, mulai dari atas layar
                                x = Random.nextFloat() * (screenWidthPx - with(density) { 40.dp.toPx() }) + with(
                                    density
                                ) { 20.dp.toPx() },
                                y = -with(density) { 20.dp.toPx() }, // Mulai sedikit di atas layar
                                radius = with(density) { 20.dp.toPx() },
                                color = Color(
                                    Random.nextInt(256),
                                    Random.nextInt(256),
                                    Random.nextInt(256)
                                ) // Warna acak
                            )
                        )
                    }

                    // Deteksi tabrakan dan perbarui skor
                    val remainingCircles = mutableListOf<Circle>()
                    newCircles.forEach { circle ->
                        val circleBottom = circle.y + circle.radius
                        val circleTop = circle.y - circle.radius
                        val paddleTop = paddle.value.y
                        val paddleBottom = paddle.value.y + paddle.value.height
                        val circleLeft = circle.x - circle.radius
                        val circleRight = circle.x + circle.radius
                        val paddleLeft = paddle.value.x
                        val paddleRight = paddle.value.x + paddle.value.width

                        // Periksa tumpang tindih di sumbu Y dan X
                        val isColliding = circleBottom >= paddleTop &&
                                circleTop <= paddleBottom &&
                                circleRight >= paddleLeft &&
                                circleLeft <= paddleRight

                        if (isColliding) {
                            score++ // Tambah skor jika lingkaran tertangkap
                        } else {
                            remainingCircles.add(circle) // Simpan lingkaran yang tidak tertangkap
                        }
                    }

                    // Perbarui list lingkaran utama dengan lingkaran yang tersisa
                    circles.clear()
                    circles.addAll(remainingCircles)
                }
            }
        }
    }

    // Penanganan input sentuh untuk menggerakkan dayung
    val pointerInputModifier = Modifier.pointerInput(Unit) {
        detectDragGestures { change, dragAmount ->
            change.consume() // Konsumsi event drag
            // Perbarui posisi X dayung, batasi agar tidak keluar layar
            paddle.value = paddle.value.copy(
                x = (paddle.value.x + dragAmount.x).coerceIn(
                    0f,
                    screenWidthPx - paddle.value.width
                )
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Tampilan skor
        Text(
            text = "Skor: $score",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        // Area permainan (Canvas)
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f) // Mengambil sisa ruang vertikal
                .then(pointerInputModifier) // Lampirkan penanganan input sentuh
        ) {
            // Gambar dayung
            drawRect(
                color = paddle.value.color,
                topLeft = Offset(paddle.value.x, paddle.value.y),
                size = Size(paddle.value.width, paddle.value.height)
            )

            // Gambar semua lingkaran yang sedang jatuh
            circles.forEach { circle ->
                drawCircle(
                    color = circle.color,
                    radius = circle.radius,
                    center = Offset(circle.x, circle.y)
                )
            }

            // Overlay Game Over
            if (gameState == GameState.GameOver) {
                // Latar belakang overlay semi-transparan
                drawRect(
                    color = Color.Black.copy(alpha = 0.5f),
                    size = size
                )

                // Teks Game Over
                val gameOverText = "Permainan Selesai!\nSkor: $score"
                val textLayoutResult: TextLayoutResult = textMeasurer.measure(
                    text = gameOverText,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 32.sp,
                        textAlign = TextAlign.Center
                    ),
                    constraints = Constraints(maxWidth = size.width.toInt()) // Batasi lebar teks ke lebar kanvas
                )

                // Gambar teks di tengah kanvas
                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(
                        x = (size.width - textLayoutResult.size.width) / 2f,
                        y = (size.height - textLayoutResult.size.height) / 2f
                    )
                )
            } else if (gameState == GameState.Ready) {
                // Teks layar "Siap"
                val readyText = "Sentuh 'Mulai' untuk bermain!"
                val textLayoutResult: TextLayoutResult = textMeasurer.measure(
                    text = readyText,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    ),
                    constraints = Constraints(maxWidth = size.width.toInt())
                )

                // Gambar teks di tengah kanvas
                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(
                        x = (size.width - textLayoutResult.size.width) / 2f,
                        y = (size.height - textLayoutResult.size.height) / 2f
                    )
                )
            }
        }

        // Tombol kontrol (Mulai/Jeda/Mulai Ulang)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = {
                    when (gameState) {
                        GameState.Ready, GameState.GameOver -> {
                            score = 0 // Reset skor
                            circles.clear() // Hapus semua lingkaran
                            // Reset posisi dayung ke tengah
                            paddle.value =
                                paddle.value.copy(x = screenWidthPx / 2 - paddle.value.width / 2)
                            gameState = GameState.Playing // Mulai permainan
                        }

                        GameState.Playing -> {
                            gameState = GameState.Ready // Jeda permainan
                        }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp) // Tinggi tetap untuk tombol
            ) {
                Text(
                    text = when (gameState) {
                        GameState.Ready -> "Mulai"
                        GameState.Playing -> "Jeda"
                        GameState.GameOver -> "Mulai Ulang"
                    }
                )
            }
        }
    }
}
