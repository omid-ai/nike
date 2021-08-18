package com.example.one.ui.productDetail.comment

import androidx.lifecycle.*
import com.example.one.model.dataClass.Comment
import com.example.one.model.repository.comment.CommentRepository
import com.example.one.util.EXTRA_KEY_PRODUCT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val commentRepository: CommentRepository
):ViewModel() {

    private val _commentsLiveData=MutableLiveData<List<Comment>>()
    val commentsLiveData:LiveData<List<Comment>> get() = _commentsLiveData

    init {
        getComment()
    }


    private fun getProductId():Int{
        return savedStateHandle.get<Int>(EXTRA_KEY_PRODUCT_ID)!!
    }

    private fun getComment(){
        viewModelScope.launch {
            val comments=commentRepository.getComments(getProductId())
            _commentsLiveData.postValue(comments)
        }
    }
}