package com.example.itemizer.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.itemizer.R
import com.example.itemizer.model.Section

@Composable
fun HomeScreen(
    itemizerUiState: ItemizerUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (itemizerUiState) {
        is ItemizerUiState.Loading -> LoadingScreen(
            modifier
                .fillMaxSize()
                .size(200.dp))
        is ItemizerUiState.Success ->
            ExpandableList(itemizerUiState.items)
        else -> ErrorScreen(retryAction, modifier.fillMaxSize())
    }
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading),
        modifier = modifier
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.loading_failed))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun ExpandableList(sections: List<Section>) {
    val isExpandedMap = remember {
        List(sections.size) { index: Int -> index to false }
            .toMutableStateMap()
    }
    LazyColumn(
        content = {
            sections.onEachIndexed { index, data ->
                Section(
                    sectionData = data,
                    isExpanded = isExpandedMap[index] ?: false,
                    onHeaderClick = {
                        isExpandedMap[index] = !(isExpandedMap[index] ?: false)
                    }
                )
            }
        }
    )
}
fun LazyListScope.Section(
    sectionData: Section,
    isExpanded: Boolean,
    onHeaderClick: () -> Unit
) {
    item {
        Column {
            SectionHeader(
                listId = sectionData.listId.toString(),
                isExpanded = isExpanded,
                onHeaderClicked = onHeaderClick
            )
            if(isExpanded) {
                ColumnNames()
            }
        }
    }
    itemsIndexed(sectionData.data) { index, item ->
        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn() + slideInVertically() + expandVertically(),
            exit = fadeOut() + slideOutVertically() + shrinkVertically()
        ) {
            ItemRow(id = item.id.toString(), name = item.name.toString())
        }
    }

}

@Composable
fun SectionHeader(
    listId: String,
    isExpanded: Boolean,
    onHeaderClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onHeaderClicked() }
            .padding(horizontal = 20.dp, vertical = 6.dp)
            .background(Color(0xFFA3E4D7), RoundedCornerShape(8.dp))
            .padding(horizontal = 20.dp, vertical = 6.dp)
            .zIndex(5F)
    )
    {
        Text(
            text = "listId: $listId",
            fontSize = 20.sp,
            modifier = Modifier.weight(1.0f),
            fontWeight = FontWeight.Medium,
        )

        //Arrow icon
        if(isExpanded) {
            Icon(
                painter = painterResource(id = R.drawable.expand_less_fill0_wght400_grad0_opsz24),
                contentDescription = "arrowUp",
            )
        }
        else {
            Icon(
                painter = painterResource(id = R.drawable.expand_more_fill0_wght400_grad0_opsz24),
                contentDescription = "arrowUp",
            )
        }

    }
}

@Composable
fun ColumnNames() {
    Row(
        modifier = Modifier
            .padding(top = 5.dp, bottom = 12.dp)
    ) {
        Text(
            text = "id:",
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        )
        Text(
            text = "name:",
            modifier = Modifier
                .weight(1f)
                .padding(end = 32.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        )
    }
}

@Composable
fun ItemRow(id: String, name: String) {
    Row(
        modifier = Modifier
            .height(30.dp)
            .fillMaxWidth()
            .padding(10.dp, 0.dp)
    ) {
        Text(
            text = id,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = name,
            modifier = Modifier
                .weight(1f)
                .padding(end = 20.dp),
            textAlign = TextAlign.Center
        )
    }
}
