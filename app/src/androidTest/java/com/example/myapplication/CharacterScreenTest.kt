package com.example.myapplication

import androidx.compose.ui.test.junit4.createComposeRule
import com.example.myapplication.ui.countries.CharactersUiState
import com.example.myapplication.ui.countries.CharactersViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Before
import org.junit.Rule

class CharactersScreenTest {
//    MockitoTest implemetation
//    private val mockViewModel = object : CharactersViewModel(FakeRepository()) {
//        private val _uiState = MutableStateFlow(CharactersUiState())
//        override val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()
//
//        fun setUiState(state: CharactersUiState) {
//            _uiState.value = state
//        }
//    }
//
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    @Before
//    fun setup() {
//        mockViewModel = mock(CharactersViewModel::class.java)
//        val uiState = MutableStateFlow(CharactersUiState())
//        whenever(mockViewModel.uiState).thenReturn(uiState)
//    }
}