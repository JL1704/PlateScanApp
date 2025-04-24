package com.deltasquad.platescanapp.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.deltasquad.platescanapp.R
import com.deltasquad.platescanapp.presentation.theme.*

/**
 * Composable que representa la barra de navegación inferior de la aplicación.
 *
 * @param selectedItem Índice del elemento seleccionado.
 * @param onItemSelected Callback al seleccionar un ítem.
 * @param modifier Modificador para personalización opcional.
 */
@Composable
fun BottomNavigationView(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem("Home", R.drawable.ic_home),
        BottomNavItem("Camera", R.drawable.ic_camera),
        BottomNavItem("Profile", R.drawable.ic_profile)
    )

    NavigationBar(
        containerColor = primaryGreen,
        modifier = modifier
            .fillMaxWidth()
            .height(PlateScanAppTheme.dimens.bottomNavViewHeight)
            .navigationBarsPadding()
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        tint = primaryWhite
                    )
                },
                label = {
                    Text(item.label, color = primaryWhite)
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = primaryWhite,
                    unselectedIconColor = primaryWhite,
                    indicatorColor = primaryBrown
                )
            )
        }
    }
}

/**
 * Representa un ítem en la barra de navegación.
 */
data class BottomNavItem(val label: String, val icon: Int)

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    PlateScanAppTheme {
        BottomNavigationView(selectedItem = 2, onItemSelected = {})
    }
}




