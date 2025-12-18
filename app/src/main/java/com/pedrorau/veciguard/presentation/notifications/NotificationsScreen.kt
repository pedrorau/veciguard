package com.pedrorau.veciguard.presentation.notifications

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.NoiseAware
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NotificationsScreen(
    onBackButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = onBackButtonPressed)

    val notifications = remember {
        listOf(
            Notification(
                id = "1",
                title = "Robo en progreso",
                time = "Hace 2 min",
                distance = "a 150m",
                type = NotificationType.DANGER,
                isRead = true
            ),
            Notification(
                id = "2",
                title = "Actividad Sospechosa",
                time = "Hace 15 min",
                distance = "a 500m",
                type = NotificationType.SUSPICIOUS,
                isRead = true
            ),
            Notification(
                id = "3",
                title = "Ruido excesivo",
                time = "Hace 1 hora",
                distance = "a 800m",
                type = NotificationType.NOISE,
                isRead = true
            ),
            Notification(
                id = "4",
                title = "Mascota perdida",
                time = "Hace 2 horas",
                distance = "a 1.2km",
                type = NotificationType.PET,
                isRead = true
            ),
            Notification(
                id = "5",
                title = "Posible Robo",
                time = "Hace 3 horas",
                distance = "a 900m",
                type = NotificationType.DANGER,
                isRead = true
            ),
            Notification(
                id = "6",
                title = "Vandalismo",
                time = "Hace 4 horas",
                distance = "a 200m",
                type = NotificationType.INFO,
                isRead = true
            )
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        items(notifications.take(6)) { notification ->
            NotificationItem(
                notification = notification,
                onClick = {  }
            )
        }
    }
}

@Composable
fun NotificationItem(
    notification: Notification,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        color = notification.type.backgroundColor,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = notification.type.icon,
                    contentDescription = null,
                    tint = notification.type.iconColor,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${notification.time} • ${notification.distance}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            if (notification.isRead) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Leído",
                    tint = Color.LightGray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

data class Notification(
    val id: String,
    val title: String,
    val time: String,
    val distance: String,
    val type: NotificationType,
    val isRead: Boolean
)

enum class NotificationType(
    val backgroundColor: Color,
    val iconColor: Color,
    val icon: ImageVector
) {
    DANGER(
        backgroundColor = Color(0xFFFFEBEE),
        iconColor = Color(0xFFE53935),
        icon = Icons.Default.Shield
    ),
    SUSPICIOUS(
        backgroundColor = Color(0xFFFFF3E0),
        iconColor = Color(0xFFFFA726),
        icon = Icons.Default.Warning
    ),
    PET(
        backgroundColor = Color(0xFFE8F5E9),
        iconColor = Color(0xFF66BB6A),
        icon = Icons.Default.Pets
    ),
    INFO(
        backgroundColor = Color(0xFFFFF3E0),
        iconColor = Color(0xFFFFA726),
        icon = Icons.Default.Info
    ),
    NOISE(
        backgroundColor = Color(0xFFFFF3E0),
        iconColor = Color(0xFFFFA726),
        icon = Icons.Default.NoiseAware
    ),
}

@Preview(showBackground = true)
@Composable
fun NotificationsScreenPreview() {
    MaterialTheme {
        NotificationsScreen(
            onBackButtonPressed = {}
        )
    }
}