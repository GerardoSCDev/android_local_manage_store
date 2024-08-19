package com.example.localmanagestore.Modules.StartApp.Screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localmanagestore.CommonUtils.AppComponents.AppButton.AppButton
import com.example.localmanagestore.CommonUtils.AppComponents.AppButton.AppButtonType
import com.example.localmanagestore.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeScreen(pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF032030)),
    ) {
        Column (
            verticalArrangement = Arrangement.spacedBy(25.dp),
            modifier = Modifier
                .padding(25.dp)
                .fillMaxSize()
                .background(Color(0xFF032030)),
        ) {
            Text(
                text = "Bienvenido a miTiendita",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(align = Alignment.CenterVertically),
                style = TextStyle(fontSize = 23.sp, fontWeight = FontWeight.Bold, color = Color.White)
            )
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {

                Image(
                    painter = painterResource(id = R.drawable.welcome_image),
                    contentDescription = null,
                    modifier = Modifier.padding(5.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    text = "Estamos emocionados de que pruebes nuestra solución de inventario."
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    text = "Optimiza y gestiona tus productos de manera fácil."
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    text = "¡Empecemos!",
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                AppButton(
                    type = AppButtonType.WhiteButton,
                    title = "Continuar",
                    onClick = {
                        coroutineScope.launch {
                            pagerState.scrollToPage(1)
                        }
                    }
                )
            }
        }
    }
}