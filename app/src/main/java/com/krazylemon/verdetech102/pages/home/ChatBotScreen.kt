package com.krazylemon.verdetech102.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krazylemon.verdetech102.ApiViewModel
import com.krazylemon.verdetech102.R
import com.krazylemon.verdetech102.models.MessageModel
import com.krazylemon.verdetech102.ui.theme.Turquoise30

@Composable
fun ChatbotScreen(ApiViewModel: ApiViewModel)  {
    Column(

    ){
        ChatHeader()
        MessageInput(onMessageSend = { ApiViewModel.sendMessage(it) })
        MessageList(messageList = ApiViewModel.messageList)
    }
}

@Composable
fun MessageList(modifier: Modifier = Modifier,messageList : List<MessageModel>){
    if(messageList.isEmpty()){
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_question),
                contentDescription = "Fondo",
            )
        }
    }else{
        LazyColumn(
            modifier = modifier,
            reverseLayout = true
        ) {
            items(messageList.reversed()){
                MessageRow(messageModel = it)
            }
        }
    }
}

@Composable
fun MessageRow(messageModel : MessageModel) {
    val isModel = messageModel.role=="model"

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Box(
                modifier = Modifier
                    .align(
                        if (isModel) Alignment.BottomStart else Alignment.BottomEnd
                    )
                    .padding(
                        start = if (isModel) 8.dp else 70.dp,
                        end = if (isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(48f))
                    .background(
                        if (isModel) Turquoise30 else MaterialTheme.colorScheme.primary
                    )
                    .padding(16.dp)
            ){
                Text(
                    text = messageModel.message,
                    fontWeight = FontWeight.W500,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun MessageInput(onMessageSend: (String)-> Unit){
    var message by remember{
        mutableStateOf("")
    }
    Row(
        modifier = Modifier
            .padding(top = 8.dp, start = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = message,
            onValueChange = { message = it }
        )
        IconButton(
            onClick = {
                if(message.isNotEmpty()){
                    onMessageSend(message)
                    message = ""
                }
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send"
            )
        }
    }
}

@Composable
fun ChatHeader(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ){
       Text(text = "Plant Bot",
           modifier = Modifier.padding(16.dp),
           color = MaterialTheme.colorScheme.background,
           fontSize = 22.sp
           )
    }
}
