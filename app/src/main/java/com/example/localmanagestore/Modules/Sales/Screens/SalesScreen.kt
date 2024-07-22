package com.example.localmanagestore.Modules.Sales.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.localmanagestore.CommonUtils.AppComponents.AppButtonNavBar.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesScreen() {
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(text = AppScreens.Sales.name) }
            )
        }
    ) { paddingValues ->



        ListWithColum(
            item = listOf(
                "OPT1aaaa", "OPT2aaaa", "OPT1", "OPT2","OPT1", "OPT2", "OPT1", "OPT2","OPT1", "OPT2", "OPT1", "OPT2","OPT1", "OPT2", "OPT1", "OPT2","OPT1", "OPT2", "OPT1", "OPT2","OPT1", "OPT2", "OPT1", "OPT2","OPT1", "OPT2", "OPT1", "OPT2","OPT1", "OPT2", "OPT1", "OPT2"
            )
        )
    }
}

@Composable
fun ListWithColum(item: List<String>) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ){
        item.forEach { item ->

            ListItemRow(item)

        }
    }
}

@Composable // (3)
fun ListItemRow(item: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text = item, style = MaterialTheme.typography.bodyLarge)
    }
}