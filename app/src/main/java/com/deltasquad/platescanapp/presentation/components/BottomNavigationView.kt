package com.deltasquad.platescanapp.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.painterResource
import com.deltasquad.platescanapp.presentation.theme.*

/**
 * Composable que representa la barra de navegación inferior de la aplicación.
 *
 * @param selectedItem Índice del elemento seleccionado en la barra de navegación.
 * @param onItemSelected Callback que se ejecuta cuando se selecciona un elemento.
 * @param modifier Modificador opcional para personalizar la apariencia del componente.
 */
@Composable
fun BottomNavigationView(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // Lista de elementos que se mostrarán en la barra de navegación.
    val items = listOf(
        BottomNavItem("Home", com.deltasquad.platescanapp.R.drawable.ic_home),
        BottomNavItem("Camera", com.deltasquad.platescanapp.R.drawable.ic_camera),
        BottomNavItem("Profile", com.deltasquad.platescanapp.R.drawable.ic_profile)
    )

    // Barra de navegación inferior con diseño personalizado.
    NavigationBar(
        containerColor = primaryGreen,
        modifier = Modifier
            .fillMaxWidth()
            .height(PlateScanAppTheme.dimens.bottomNavViewHeight)
            .navigationBarsPadding()
    ) {
        // Iteramos sobre los elementos y creamos los botones de la barra de navegación.
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
                    Text(
                        item.label,
                        color = primaryWhite
                    )
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
 * Representa un elemento dentro de la barra de navegación inferior.
 *
 * @param label Texto que describe el elemento.
 * @param icon ID del recurso del icono asociado.
 */
data class BottomNavItem(val label: String, val icon: Int)

/**
 * Vista previa del componente BottomNavigationView con un elemento preseleccionado.
 */
@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    PlateScanAppTheme {
        BottomNavigationView(selectedItem = 2, onItemSelected = {})
    }
}




