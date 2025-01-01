package com.example.littlelemon.ui.screen

import android.annotation.SuppressLint
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.littlelemon.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.data.model.MenuItemEntity
import com.example.littlelemon.data.model.MenuViewModel
import com.example.littlelemon.ui.theme.app.AppTheme

@Composable
fun Home(navController: NavController, viewModel: MenuViewModel = viewModel()) {
    var searchPhrase by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    val menuItems by viewModel.menuData.collectAsState()

    // Search
    val filteredItems = menuItems.filter { item ->
        (selectedCategory == null || item.category == selectedCategory) &&
                (item.title.contains(searchPhrase, ignoreCase = true) ||
                        item.description.contains(searchPhrase, ignoreCase = true))
    }

    Column {
        HeroSection(onSearch = { query -> searchPhrase = query })
        MenuBreakdown(menuItems = menuItems) { category ->
            selectedCategory = category
        }
        FilteredMenuItems(
            menuItems = menuItems,
            selectedCategory = selectedCategory,
            searchPhrase = searchPhrase
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Home()
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnsafeOptInUsageError")
@Composable
fun HeroSection(onSearch: (String) -> Unit) {
    var query by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(color = AppTheme.color.primary1)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.home_hero_section_title),
            style = AppTheme.typography.display,
            color = AppTheme.color.primary2,
        )
        Text(
            text = stringResource(R.string.home_hero_section_subtitle),
            style = AppTheme.typography.subTitle,
            color = AppTheme.color.highlight1,
            modifier = Modifier
                .offset(y = (-15).dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f)
                    .width(0.dp)
                    .align(Alignment.CenterVertically),
                text = stringResource(R.string.home_hero_section_about),
                style = AppTheme.typography.highlight,
                color = AppTheme.color.highlight1
            )
            Image(
                modifier = Modifier
                    .width(100.dp)
                    .height(120.dp)
                    .clip(MaterialTheme.shapes.medium),
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "hero image",
                contentScale = ContentScale.Crop,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = query,
            onValueChange = {
                query = it
                if (it.isBlank())
                    onSearch(it)
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.home_search_bar_hint),
                    style = AppTheme.typography.leadText,
                )
            },
            textStyle = AppTheme.typography.leadText,
            singleLine = true,
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    imageVector = Icons.Default.Search,
                    contentDescription = "",
                    tint = Color.Black
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearch(query) }
            ),
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.LightGray,
                cursorColor = AppTheme.color.primary1
            )
        )
    }
}


@Composable
fun MenuItems(menuItems: List<MenuItemEntity>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        menuItems.forEach { item ->
            MenuItem(item = item)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(item: MenuItemEntity) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = item.title,
            style = AppTheme.typography.cardTitle,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .width(0.dp)
            ) {
                Text(
                    text = item.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = AppTheme.typography.paragraph,
                    modifier = Modifier
                        .padding(top = 8.dp, end = 8.dp, bottom = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.menu_item_price, item.price),
                    style = AppTheme.typography.leadText
                )
            }
            GlideImage(
                model = item.image,
                contentDescription = "Food Image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 24.dp)
                .height(1.dp),
            color = Color.LightGray
        )
    }
}

@Composable
fun MenuBreakdown(
    menuItems: List<MenuItemEntity>,
    onCategorySelected: (String) -> Unit
) {
    // Group items by category
    val categories = menuItems
        .groupBy { it.category }
        .keys
        .toList()

    // Display category buttons
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        categories.forEach { category ->
            Button(
                onClick = {
                    onCategorySelected(category) // Filter items based on category
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text(text = category)
            }
        }
    }
}

@Composable
fun FilteredMenuItems(
    menuItems: List<MenuItemEntity>,
    selectedCategory: String?,
    searchPhrase: String
) {
    // Filter items by category and search phrase
    val filteredItems = menuItems.filter { item ->
        (selectedCategory == null || item.category == selectedCategory) &&
                (item.title.contains(searchPhrase, ignoreCase = true) ||
                        item.description.contains(searchPhrase, ignoreCase = true))
    }

    // Display filtered items
    MenuItems(menuItems = filteredItems)
}



