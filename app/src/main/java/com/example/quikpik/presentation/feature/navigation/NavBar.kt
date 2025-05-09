package com.example.quikpik.presentation.feature.navigation

import android.R.attr.contentDescription
import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.quikpik.common.Screen

data class BottomItem (
    val title: String,
    val icon: ImageVector,
    val filled: ImageVector
)

@Composable
fun BottomNavigationBar(
    bottomNavController: NavHostController,
) {
    val items = listOf(
        BottomItem(
            title = "Home",
            icon = Icons.Outlined.Home,
            filled = Icons.Rounded.Home
        ),
        BottomItem(
            title = "Message",
            icon = Icons.Outlined.MailOutline,
            filled =Icons.Rounded.MailOutline
        ),
        BottomItem(
            title = "Post",
            icon = Icons.Outlined.Add,
            filled = Icons.Rounded.Add
        ),
        BottomItem(
            title = "Explore",
            icon = Icons.Outlined.Search,
            filled = Icons.Rounded.Search
        ),
        BottomItem(
            title = "Profile",
            icon = Icons.Outlined.Person,
            filled = Icons.Rounded.Person
        )
    )

    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            items.forEachIndexed { index, bottomItem ->
                NavigationBarItem(
                    selected = selected.intValue == index,
                    onClick = {
                        selected.intValue = index
                        when (selected.intValue) {
                            0 -> {

                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screen.Home.route)
                            }
                            1 -> {

                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screen.Messages.route)
                            }
                            2 -> {

                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screen.Post.route)
                            }
                            3 -> {

                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screen.Explore.route)
                            }
                            4 -> {

                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screen.Profile.route)
                            }

                        }
                    },
                    icon = {
                        Icon(
                            imageVector = if(selected.intValue==index) bottomItem.filled else bottomItem.icon,
                            contentDescription = bottomItem.title,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    label = {
                        Text(
                            text = bottomItem.title,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }
        }
    }
}