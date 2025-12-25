package com.example.gameui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gameui.ui.theme.GameColors

@Composable
fun EditProfileScreen(
    username: String,
    bio: String,
    onBackClick: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var editedUsername by remember { mutableStateOf(username) }
    var editedBio by remember { mutableStateOf(bio) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GameColors.BackgroundLight)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                .background(
                    brush = Brush. verticalGradient(
                        colors = listOf(
                            GameColors.AccentPurple,
                            GameColors. AccentBlue
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier. fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .clickable { onBackClick() },
                    contentAlignment = Alignment. Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = "返回",
                        tint = Color.White,
                        modifier = Modifier. size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "编辑资料",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment. Center
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        . border(3.dp, GameColors.AccentPurple, CircleShape)
                        . background(GameColors.BackgroundGray)
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default. Person,
                        contentDescription = null,
                        tint = GameColors.TextMuted,
                        modifier = Modifier.size(60.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = (-40).dp, y = (-8).dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(GameColors.AccentPurple)
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "编辑头像",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier. height(16.dp))

            EditField(
                label = "用户名",
                value = editedUsername,
                onValueChange = { editedUsername = it },
                placeholder = "请输入用户名"
            )

            Spacer(modifier = Modifier.height(16.dp))

            EditField(
                label = "个人简介",
                value = editedBio,
                onValueChange = { editedBio = it },
                placeholder = "介绍一下自己吧",
                singleLine = false,
                maxLines = 4
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    onSave(editedUsername, editedBio)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GameColors.AccentPurple
                )
            ) {
                Text(
                    text = "保存",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun EditField(
    label:  String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder:  String,
    singleLine:  Boolean = true,
    maxLines: Int = 1
) {
    Column {
        Text(
            text = label,
            color = GameColors.TextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier. height(8.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier. fillMaxWidth(),
            placeholder = {
                Text(
                    text = placeholder,
                    color = GameColors.TextMuted
                )
            },
            singleLine = singleLine,
            maxLines = maxLines,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GameColors.AccentPurple,
                unfocusedBorderColor = GameColors. BorderLight,
                focusedContainerColor = GameColors.CardBackground,
                unfocusedContainerColor = GameColors.CardBackground
            )
        )
    }
}