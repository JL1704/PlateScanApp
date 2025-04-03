import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.painterResource
import com.deltasquad.platescanapp.presentation.theme.*

@Composable
fun BottomNavigationView(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem("Home", com.deltasquad.platescanapp.R.drawable.ic_home),
        BottomNavItem("Camera", com.deltasquad.platescanapp.R.drawable.ic_camera),
        BottomNavItem("Profile", com.deltasquad.platescanapp.R.drawable.ic_profile)
    )

    NavigationBar(
        containerColor = primaryGreen,
        modifier = Modifier
            .fillMaxWidth()
            .height(PlateScanAppTheme.dimens.bottomNavViewNormal)
            .navigationBarsPadding() // Asegura que no se solape con la barra de navegación del sistema
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

data class BottomNavItem(val label: String, val icon: Int)

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    PlateScanAppTheme {
        BottomNavigationView(selectedItem = 2, onItemSelected = {})
    }
}



