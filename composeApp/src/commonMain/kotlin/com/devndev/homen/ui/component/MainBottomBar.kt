package com.devndev.homen.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.devndev.homen.ui.main.navigation.BottomNavItem
import com.devndev.homen.ui.theme.HomeNTheme
import com.devndev.homen.ui.theme.LightGray
import org.jetbrains.compose.resources.painterResource

@Composable
fun MainBottomBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Board,
        BottomNavItem.Assignment,
        BottomNavItem.Present,
        BottomNavItem.MyPage
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp), 
        color = Color.White
    ) {
        Column {
            // 최상단 경계선 추가
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 0.2.dp,
                color = Color.Black // 은은한 구분선
            )
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    val isSelected = currentRoute == item.route
                    
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null, 
                                onClick = {
                                    if (currentRoute != item.route) {
                                        navController.navigate(item.route) {
                                            navController.graph.startDestinationRoute?.let { route ->
                                                popUpTo(route) { saveState = true }
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                }
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = item.title,
                            modifier = Modifier.size(24.dp),
                            tint = if (isSelected) Color.Black else LightGray
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Text(
                            text = item.title,
                            style = HomeNTheme.typography.suitRegular,
                            fontSize = 10.sp,
                            color = if (isSelected) Color.Black else LightGray
                        )
                    }
                }
            }
        }
    }
}
