package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.data.AppDatabase
import com.example.littlelemon.data.MenuItem
import com.example.littlelemon.data.SampleData
import com.example.littlelemon.ui.theme.HighlightLight
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun HomeScreen(navHostController: NavHostController, modifier: Modifier = Modifier) {
    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    val menuItems by AppDatabase.getDatabase(LocalContext.current).menuDao().getAllMenuItems()
        .observeAsState(listOf())

    val menuCategories = menuItems
        .map { it.category }
        .filter { it.isNotEmpty() }
        .distinct()
        .map { it.capitalize(Locale.current) }

    var filteredItems = if (selectedCategory != null) {
        menuItems.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    } else {
        menuItems
    }

    if (searchText.isNotBlank()) {
        filteredItems = filteredItems.filter { it.title.contains(searchText, ignoreCase = true) }
    }

    HomeScreenContent(
        modifier,
        onProfileClick = {
            navHostController.navigate(Profile.route)
        },
        searchText = searchText,
        onSearch = { searchText = it },
        menuCategories = menuCategories,
        selectedCategory = selectedCategory,
        onCategorySelected = { selectedCategory = it },
        menuItems = filteredItems
    )
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit = {},
    searchText: String = "",
    onSearch: (String) -> Unit = {},
    menuCategories: List<String> = listOf(),
    selectedCategory: String? = null,
    onCategorySelected: (String?) -> Unit = {},
    menuItems: List<MenuItem> = listOf()
) {
    Scaffold(modifier = modifier, topBar = {
        LittleLemonTopAppBar(actions = {
            IconButton(onClick = onProfileClick) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile Image",
                    modifier = Modifier.clip(CircleShape)
                )
            }
        })
    }) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            HeroSection {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = onSearch,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    ),
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search Icon")
                    },
                    singleLine = true,
                    placeholder = { Text("Search dishes...") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )
            }
            MenuBreakDown(
                menuCategories = menuCategories,
                selectedCategory = selectedCategory,
                onCategorySelected = onCategorySelected
            )
            HorizontalDivider()
            MenuItems(menuItems = menuItems)
        }
    }
}

@Composable
fun MenuItems(modifier: Modifier = Modifier, menuItems: List<MenuItem> = listOf()) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        menuItems.forEach { menuItem ->
            MenuItem(
                title = menuItem.title,
                description = menuItem.description,
                price = menuItem.price,
                image = menuItem.image
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    title: String = "",
    description: String = "",
    price: String = "",
    image: Any? = null
) {
    Row(modifier = modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1F)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(
                description,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 2
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                "$ $price",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium
                )
            )
        }
        GlideImage(
            image,
            contentDescription = "Greek Salad",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .sizeIn(maxWidth = 70.dp, maxHeight = 70.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MenuItemPreview() {
    LittleLemonTheme {
        MenuItem(
            title = "Greek Salad",
            description = "The famous greek salad of crispy lettuce, peppers, olives and our Chicago...",
            price = "12.99",
            image = R.drawable.hero_image
        )
    }
}

@Composable
fun HeroSection(modifier: Modifier = Modifier, content: @Composable () -> Unit = {}) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(300.dp)
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 16.dp, vertical = 20.dp),
    ) {
        Column {
            Text(
                "Little Lemon",
                color = MaterialTheme.colorScheme.secondaryContainer,
                style = MaterialTheme.typography.displayLarge
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    modifier = Modifier.weight(1F),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        "Chicago",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.displayMedium
                    )
                    Text(
                        "We are a family owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.hero_image),
                    contentDescription = "Hero Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .sizeIn(maxWidth = 120.dp, maxHeight = 160.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            content()
        }
    }
}

@Composable
fun MenuBreakDown(
    modifier: Modifier = Modifier,
    menuCategories: List<String> = listOf(),
    selectedCategory: String? = null,
    onCategorySelected: (String?) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Order for delivery!".uppercase(),
            style = MaterialTheme.typography.headlineSmall,
        )
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .selectableGroup(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            menuCategories.forEach { category ->
                val isSelected = selectedCategory == category
                Text(
                    modifier = Modifier
                        .selectable(
                            selected = isSelected,
                            onClick = {
                                val selection = if (isSelected) null else category
                                onCategorySelected(selection)
                            },
                            role = Role.Button
                        )
                        .background(
                            color = if (isSelected) MaterialTheme.colorScheme.primary else HighlightLight,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(10.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                    text = category
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuBreakDownPreview() {
    LittleLemonTheme {
        var selectedCategory by remember { mutableStateOf<String?>(null) }
        MenuBreakDown(
            menuCategories = listOf("Starters", "Mains", "Desserts", "Sides", "Drinks", "Specials"),
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it })
    }
}

@Preview(showBackground = true)
@Composable
fun HeroSectionPreview() {
    LittleLemonTheme {
        HeroSection()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    LittleLemonTheme {
        HomeScreenContent(
            menuCategories = listOf("Starters", "Mains", "Desserts", "Sides"),
            menuItems = SampleData
        )
    }
}