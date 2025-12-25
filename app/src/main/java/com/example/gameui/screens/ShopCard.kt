package com.example.gameui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.example.gameui.model.Equipment
import androidx.compose.ui.unit.sp

@Composable
fun ShopCard(
    equipment: Equipment,
    owned: Boolean,
    affordable: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x22FFFFFF))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // icon box
            val icon = when (equipment.type) {
                "physique" -> Icons.Default.FitnessCenter
                "cultivation" -> Icons.Default.MenuBook
                "intelligence" -> Icons.Default.AutoAwesome
                else -> Icons.Default.AutoAwesome
            }

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(equipment.colorForType().copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = equipment.colorForType())
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(equipment.name, color = Color.Black, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = equipment.type,
                        color = equipment.colorForType(),
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(equipment.colorForType().copy(alpha = 0.12f))
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(equipment.description, color = Color.Black.copy(alpha = 0.9f), fontSize = MaterialTheme.typography.bodySmall.fontSize)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFD54F))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("${equipment.price}", color = Color(0xFFFFD54F), fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            val buttonText = when {
                owned -> "已拥有"
                affordable -> "兑换"
                else -> "星星不足"
            }

            Button(
                onClick = onClick,
                enabled = !owned && affordable,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!owned && affordable) Color(0xFFD34CFF) else Color(0xFF444359)
                ),
                modifier = Modifier.height(36.dp)
            ) {
                Text(buttonText, color = Color.White)
            }
        }
    }
}