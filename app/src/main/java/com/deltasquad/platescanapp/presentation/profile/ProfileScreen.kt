package com.deltasquad.platescanapp.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deltasquad.platescanapp.presentation.components.CircleImageView
import com.deltasquad.platescanapp.presentation.components.UserInfo
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.deltasquad.platescanapp.presentation.theme.primaryGreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onLogout: () -> Unit,
    onEditProfile: () -> Unit
) {
    val profileState by viewModel.profile.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    val scrollState = rememberScrollState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            viewModel.refreshProfile()
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            CircleImageView(
                imageUrl = profileState?.image ?: "https://default.image.url",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))

            UserInfo(
                username = profileState?.username ?: "Loading...",
                email = profileState?.email ?: "",
                phone = profileState?.phone ?: ""
            )

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onEditProfile,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        primaryGreen
                    )
                ) {
                    Text("Edit",  color = Color.White, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = onLogout,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        Color.Red
                    )
                ) {
                    Text("Log Out", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
