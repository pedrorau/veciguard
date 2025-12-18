package com.pedrorau.veciguard.presentation.map

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState
import com.pedrorau.veciguard.R

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun MapScreen(
    onBackButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = onBackButtonPressed)

    var selectedFilter by remember { mutableStateOf("Todos") }
    var showAlternativeView by remember { mutableStateOf(false) }

    val filters = listOf(
        FilterChip("Todos", Color(0xFFE8EAF6), Color(0xFF5C6BC0)),
        FilterChip("Peligro", Color(0xFFFFEBEE), Color(0xFFE53935)),
        FilterChip("Sospechoso", Color(0xFFFFF3E0), Color(0xFFFFA726)),
        FilterChip("Resuelto", Color(0xFFE8F5E9), Color(0xFF66BB6A))
    )

    val initialPosition = LatLng(-12.0464, -77.0428)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialPosition, 12f)
    }

    val incidents = remember {
        listOf(
            Incident(LatLng(-12.0464, -77.0428), "Peligro", "Robo 1"),
            Incident(LatLng(-12.0500, -77.0500), "Sospechoso", "Persona sospechosa 1"),
            Incident(LatLng(-12.0550, -77.0550), "Resuelto", "Caso resuelto 1"),

            Incident(LatLng(-12.0600, -77.0600), "Peligro", "Robo 2"),
            Incident(LatLng(-12.0650, -77.0650), "Sospechoso", "Persona sospechosa 2"),
            Incident(LatLng(-12.0700, -77.0700), "Resuelto", "Caso resuelto 2"),

            Incident(LatLng(-12.0750, -77.0750), "Peligro", "Robo 3"),
            Incident(LatLng(-12.0800, -77.0800), "Sospechoso", "Persona sospechosa 3"),
            Incident(LatLng(-12.0850, -77.0850), "Resuelto", "Caso resuelto 3")
        )
    } // fecha para filtro de fecha

    val additionalIncidents = remember {
        listOf(
            Incident(LatLng(-12.0400, -77.0400), "Especial", "Cámara 1"),
            Incident(LatLng(-12.0450, -77.0450), "Especial", "Cámara 2"),
            Incident(LatLng(-12.0520, -77.0520), "Especial", "Patrulla 1"),
        )
    }

    val filteredIncidents = if (selectedFilter == "Todos") {
        incidents
    } else {
        incidents.filter { it.type == selectedFilter }
    }

    val context = LocalContext.current

    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                    context,
                    R.raw.map_style
                )
            )
        )
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filters) { filter ->
                FilterChipItem(
                    label = filter.label,
                    isSelected = selectedFilter == filter.label,
                    backgroundColor = filter.backgroundColor,
                    textColor = filter.textColor,
                    onClick = { selectedFilter = filter.label }
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = mapProperties,
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = true,
                    myLocationButtonEnabled = false,
                    mapToolbarEnabled = false,
                    compassEnabled = true,
                    zoomGesturesEnabled = true,
                    scrollGesturesEnabled = true
                ),
            ) {
                MapEffect(Unit) { map ->
                    map.setPadding(
                        0,
                        0,
                        0,
                        200
                    )
                }
                Clustering(
                    items = filteredIncidents,
                    onClusterClick = { cluster ->
                        val boundsBuilder = LatLngBounds.builder()
                        cluster.items.forEach { boundsBuilder.include(it.position) }

                        cameraPositionState.move(
                            CameraUpdateFactory.newLatLngBounds(
                                boundsBuilder.build(),
                                100
                            )
                        )
                        true
                    },
                    onClusterItemClick = { item ->
                        false
                    },
                )
            }

            FloatingActionButton(
                onClick = { showAlternativeView = !showAlternativeView },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                containerColor = if (showAlternativeView) Color(0xFF5C6BC0) else Color.White,
                contentColor = if (showAlternativeView) Color.White else Color.Gray
            ) {
                Icon(
                    imageVector = Icons.Default.Layers,
                    contentDescription = "Cambiar vista"
                )
            }
        }
    }
}

@Composable
fun FilterChipItem(
    label: String,
    isSelected: Boolean,
    backgroundColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) backgroundColor else Color(0xFFF5F5F5),
        border = if (isSelected) BorderStroke(1.dp, textColor.copy(alpha = 0.3f)) else null,
        modifier = Modifier.height(40.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            if (label != "Todos") {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            color = textColor,
                            shape = CircleShape
                        )
                )
            } else {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = null,
                    tint = textColor,
                    modifier = Modifier.size(18.dp)
                )
            }

            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) textColor else Color.Gray
            )
        }
    }
}

data class FilterChip(
    val label: String,
    val backgroundColor: Color,
    val textColor: Color
)

data class Incident(
    val location: LatLng,
    val type: String,
    val incidentTitle: String
): ClusterItem {
    override fun getPosition(): LatLng = location
    override fun getTitle(): String = incidentTitle
    override fun getSnippet(): String = type
    override fun getZIndex(): Float = 0f
}

@Preview(showBackground = true)
@Composable
fun MapScreenPreview() {
    MaterialTheme {
        MapScreen(
            onBackButtonPressed = {}
        )
    }
}