package com.localmanagestore.Modules.StartApp.Screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.localmanagestore.CommonUtils.AppComponents.AppButton.AppButton
import com.localmanagestore.CommonUtils.AppComponents.AppButton.AppButtonType
import com.localmanagestore.CommonUtils.Utils.PreferencesManager.PreferencesManager
import com.localmanagestore.modalPrivacyStrings
import com.localmanagestore.privacyIndex
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PrivacyPolicyScreen(pagerState: PagerState) {
    val currentContext = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val checkedPrivacy = remember { mutableStateOf(false) }
    val preferencesManager = remember { PreferencesManager(currentContext) }

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
                text = modalPrivacyStrings.privacyTitle,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .height(30.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(align = Alignment.CenterVertically),
                style = TextStyle(fontSize = 23.sp, fontWeight = FontWeight.Bold, color = Color.White)
            )
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .weight(1f),
                ) {
                    Text(text = modalPrivacyStrings.generalDetail, modifier = Modifier.padding(horizontal = 15.dp).padding(top = 20.dp))
                    privacyIndex.forEach { privacyIndex ->
                        Text(text = privacyIndex.title, style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold), modifier = Modifier.padding(horizontal = 15.dp))
                        privacyIndex.subtitles.forEach {
                            if ( it.textSubTitle.isNotEmpty() ) {
                                Text(text = it.textSubTitle, style = TextStyle(fontSize = 15.sp), modifier = Modifier.padding(horizontal = 15.dp))
                            }
                            if ( it.desc.isNotEmpty() ) {
                                Text(text = it.desc, textAlign = TextAlign.Justify, style = TextStyle(fontSize = 12.sp, textDirection = TextDirection.Content), modifier = Modifier.padding(horizontal = 15.dp))
                            }

                        }
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = checkedPrivacy.value,
                    onCheckedChange = { checkedPrivacy.value = it }
                )
                Text(
                    "He leido y acepto la política de privacidad",
                    color = Color.White
                )
            }
            AppButton(
                type = AppButtonType.WhiteButton,
                enabled = checkedPrivacy.value,
                title = "Aceptar",
                onClick = {
                    preferencesManager.saveData("isFirstTimeKey", "false")
                    coroutineScope.launch {
                        pagerState.scrollToPage(2)
                    }
                }
            )
        }
    }

}