package com.example.puzzle

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun Puzzle(){
    val viewModel = MainViewModel()
    Scaffold(topBar = { TopBar() }, ) {paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            if(viewModel.isPlaying){
            Card(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp).height(100.dp).fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimaryContainer),
                content = {
                    Column { Text(text = "Puzzle Solved!!", color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(10.dp), style = MaterialTheme.typography.headlineLarge)
                        Text(text = "Solved in ${viewModel.moves} moves!", color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(start = 10.dp), style = MaterialTheme.typography.bodyMedium)}
                }
            )}
            Spacer(Modifier.padding(vertical = 10.dp))
            Row(
                modifier = Modifier.padding(start = 70.dp, end = 70.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Card(
                    modifier = Modifier.width(120.dp).height(50.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    content = {
                        Column { Text(text = "Moves")
                        Text("${viewModel.moves}")}
                    }
                )
                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                Card(
                    modifier = Modifier.width(120.dp).height(50.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    content = {
                        Column { Text(text = "status")
                            if (viewModel.isPlaying) {
                                Text("Solved")
                            }else{
                                Text("Playing")
                            }




                        }
                    }
                )
            }
            Spacer(Modifier.padding(vertical = 10.dp))
            Button(onClick = { viewModel.Reorder()},
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(start = 120.dp, end = 120.dp)
                    .fillMaxWidth()) {
                Text(text = "New Puzzle")
            }
            Spacer(Modifier.padding(vertical = 10.dp))
            TileSwapExample(
                viewModel = viewModel
            )



        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(){
    TopAppBar(title = { Text("Sliding Number Puzzle") })

}

@Composable
fun TileSwapExample(viewModel: MainViewModel) {
    // Tiles stored as a flat list: [1, 2, 3, 0] represents a 2x2 grid
    // 0 represents the empty space that tiles can swap with

    // Track which tile is being dragged
    var draggedIndex by remember { mutableStateOf<Int?>(null) }
    // Track drag offset for visual feedback
    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    val gridSize = 3 // 3x3 grid (3 columns, 3 rows)
    viewModel.isPlaying=viewModel.CheckOrder(viewModel.tiles)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // 3x3 Grid layout
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(gridSize) { row ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(gridSize) { col ->
                        // Calculate flat list index from row/col: row * columns + col
                        // Example: row 1, col 0 = 1*2+0 = index 2
                        val index = row * gridSize + col
                        val tileValue = viewModel.tiles[index] // Get the value at this position
                        val isDragging = draggedIndex == index // Is this tile being dragged?

                        val animatedOffset by animateOffsetAsState(
                            targetValue = if (isDragging) dragOffset else Offset.Zero,
                            animationSpec = if (isDragging) {
                                tween(durationMillis = 0)
                            } else {
                                spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                            }, label = "tileOffset"
                        )

                        val scale by animateFloatAsState(
                            targetValue = if (isDragging) 1.1f else 1f,
                            animationSpec = tween(durationMillis = 200), label = "tileScale"
                        )

                        val alpha by animateFloatAsState(
                            targetValue = if (isDragging) 0.8f else 1f,
                            animationSpec = tween(durationMillis = 200), label = "tileAlpha"
                        )

                        if (tileValue == 0) {
                            // Empty space: show as a gray box (no number, not draggable)
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.background,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                            )
                        } else {
                            // Tile with a number: can be dragged
                            // Tile with number
                            Card(
                                modifier = Modifier
                                    .size(100.dp)
                                    .offset {
                                        IntOffset(
                                            animatedOffset.x.roundToInt(),
                                            animatedOffset.y.roundToInt()
                                        )
                                    }
                                    .scale(scale)
                                    .alpha(alpha)
                                    .shadow(
                                        elevation = if (isDragging) 8.dp else 4.dp,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .pointerInput(index) {
                                        detectDragGestures(
                                            onDragStart = {
                                                draggedIndex = index
                                                dragOffset = Offset.Zero
                                            },
                                            onDragEnd = {
                                                // Find where the empty space (0) is located
                                                val emptyIndex = viewModel.tiles.indexOf(0)

                                                // Only swap if the dragged tile is next to the empty space
                                                if (viewModel.isAdjacent(index, emptyIndex, gridSize)) {
                                                    // Swap: move tile to empty space, make old position empty
                                                    val newTiles = viewModel.tiles.toMutableList()
                                                    newTiles[emptyIndex] = viewModel.tiles[index] // Move tile to empty space
                                                    newTiles[index] = 0 // Make old position empty
                                                    viewModel.tiles = newTiles
                                                    viewModel.moves++
                                                }

                                                // Reset drag state
                                                draggedIndex = null
                                                dragOffset = Offset.Zero
                                            },
                                            onDrag = { change, dragAmount ->
                                                change.consume()
                                                dragOffset += Offset(dragAmount.x, dragAmount.y)
                                            }
                                        )
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = tileValue.toString(),
                                        style = MaterialTheme.typography.headlineLarge,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


