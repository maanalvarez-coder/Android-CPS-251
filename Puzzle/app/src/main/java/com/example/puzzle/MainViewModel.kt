package com.example.puzzle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.collections.shuffle
import kotlin.math.abs

class MainViewModel : ViewModel() {
    var tiles by mutableStateOf(listOf(1, 2, 3, 4, 5, 6 ,7 ,0 ,8 ))
    var moves by mutableStateOf(0)
    var isPlaying by mutableStateOf(true)
    var hasWon by mutableStateOf(false)
    fun isAdjacent(index1: Int, index2: Int, gridSize: Int): Boolean {
        // Convert flat index to row/column coordinates
        val row1 = index1 / gridSize // Integer division gives row
        val col1 = index1 % gridSize  // Remainder gives column
        val row2 = index2 / gridSize
        val col2 = index2 % gridSize

        // Adjacent if: same row and columns differ by 1, OR same column and rows differ by 1
        // This means they're next to each other horizontally or vertically (not diagonally)
        return (row1 == row2 && abs(col1 - col2) == 1) ||
                (col1 == col2 && abs(row1 - row2) == 1)



        }

    fun CheckOrder(list: List<Int>): Boolean{

        val testList = listOf<Int>(1, 2, 3, 4, 5 ,6 ,7 ,8, 0)
        return list == testList
    }
    fun Reorder(){
        tiles = tiles.shuffled()
        moves = 0
    }
    }



