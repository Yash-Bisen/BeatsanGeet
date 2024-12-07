package com.example.beatsangeet.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.beatsangeet.data.domain.PremiumPlanInformation
import com.example.beatsangeet.data.domain.defaultPremiumPlans
import com.example.beatsangeet.ui.appBar.MyBottomAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PremiumScreen(
    availablePlans: List<PremiumPlanInformation> = defaultPremiumPlans,
    onPlanInformationClicked: (PremiumPlanInformation) -> Unit = {},
    navController: NavController
) {

    Scaffold(
        topBar = {
            TopAppBar({ Text(text = "Pick your Premium") })
        },
        bottomBar = {
            MyBottomAppBar(navController)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding() + 8.dp,
                bottom =innerPadding.calculateBottomPadding() +10.dp,

                ),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(availablePlans) {
                PlanInformationCard(
                    card = it,
                    onViewPlansButtonClick = { onPlanInformationClicked(it) }
                )
            }
            item {
                Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
            }
        }
    }
}


@Composable
private fun PlanInformationCard(
    card: PremiumPlanInformation,
    modifier: Modifier = Modifier,
    onViewPlansButtonClick: () -> Unit
) {
    val planHighlights = remember(card) {
        card.highlights.joinToString(separator = " • ")
    }
    val gradientBrush = remember(card) {
        // Offset values
        // x = 0.0f represents the leftmost area
        // y = 0.0f represent  the topmost area
        Brush.linearGradient(
            colors = listOf(
                card.colorInformation.gradientStartColor,
                card.colorInformation.gradientEndColor
            ),
            start = Offset(0.0f, 0.0f), // top left
            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY) // bottom right
        )
    }
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradientBrush)
                .padding(
                    vertical = 16.dp,
                    horizontal = 32.dp
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PremiumCardHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                planName = card.name,
                pricingInformation = card.pricingInformation
            )

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                text = planHighlights
            )
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors( Color.White),
                onClick = onViewPlansButtonClick
            ) {
                Text(
                    modifier = Modifier.padding(
                        vertical = 8.dp,
                        horizontal = 24.dp
                    ),
                    text = "VIEW PLANS",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = card.termsAndConditions,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.Normal
            )

        }
    }
}

@Composable
private fun PremiumCardHeader(
    planName: String,
    pricingInformation: PremiumPlanInformation.PricingInformation,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = planName,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 18.sp // a tad taller than the height of subtitle1
        )
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = pricingInformation.cost,
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = pricingInformation.term,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}