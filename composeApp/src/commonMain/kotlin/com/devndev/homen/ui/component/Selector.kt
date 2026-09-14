package com.devndev.homen.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.core.domain.model.chore.ChoreCategory
import com.devndev.homen.core.domain.model.home.AssignmentItem
import com.devndev.homen.ui.common.resource
import com.devndev.homen.ui.theme.BackgroundGray
import com.devndev.homen.ui.theme.ButtonGray
import com.devndev.homen.ui.theme.GrayCFCFCF
import com.devndev.homen.ui.theme.GrayE7
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.bottom_arrow_icon
import homen.composeapp.generated.resources.checkbox_icon
import homen.composeapp.generated.resources.chore_info_difficulty
import homen.composeapp.generated.resources.chore_info_point_days
import homen.composeapp.generated.resources.party_icon
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ChoreSelector(
    modifier: Modifier = Modifier,
    isExpandable: Boolean = true,
    assignments: List<AssignmentItem>,
    title: String,
    onChoreSelected: (assignment: AssignmentItem) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedAssignment by remember { mutableStateOf<AssignmentItem?>(null) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                if (isExpandable) {
                    isExpanded = !isExpanded
                }
            }
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!isExpandable) {
                Image(
                    painter = painterResource(Res.drawable.party_icon),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
            }

            if (selectedAssignment != null) {
                val choreResource = ChoreCategory.fromId(selectedAssignment!!.category).resource
                val infoFormat = stringResource(Res.string.chore_info_point_days)

                Image(
                    painter = painterResource(choreResource),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = title,
                    style = HomeNTheme.typography.suitBold,
                    fontSize = 14.sp,
                    color = Color.Black,
                )

                Spacer(modifier = Modifier.width(5.dp))

                Box(
                    modifier = Modifier
                        .height(17.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(ButtonGray)
                        .padding(horizontal = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = infoFormat.replace("n", selectedAssignment!!.point.toString())
                            .replace("s", selectedAssignment!!.weekdayLabel)
                            .dropLast(3),
                        fontSize = 10.sp,
                        color = Color.Black,
                        style = HomeNTheme.typography.suitBold
                    )
                }
            } else {
                Text(
                    text = title,
                    style = HomeNTheme.typography.suitRegular,
                    fontSize = 14.sp,
                    color = Color.Black,
                )
            }
            if (isExpandable) {
                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    painter = painterResource(Res.drawable.bottom_arrow_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .rotate(if (isExpanded) 180f else 0f),
                    tint = Color.Black
                )
            }
        }

        if (isExpanded) {
            HorizontalDivider(
                thickness = 0.5.dp,
                color = GrayCFCFCF
            )
        }
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column {

                Box(
                    modifier = Modifier
                        .heightIn(max = 190.dp) // Roughly 3 items
                ) {
                    LazyColumn(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(assignments) { assignment ->
                            ChoreSelectionItem(
                                assignment = assignment,
                                isSelected = selectedAssignment?.id == assignment.id,
                                onSelect = {
                                    selectedAssignment = assignment
                                    onChoreSelected(assignment)
                                    isExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ChoreSelectionItem(
    assignment: AssignmentItem,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val choreResource = ChoreCategory.fromId(assignment.category).resource
    val infoFormat = stringResource(Res.string.chore_info_point_days)
    val diffFormat = stringResource(Res.string.chore_info_difficulty)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onSelect() },
    ) {
        // Icon
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(BackgroundGray),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(choreResource),
                contentDescription = null,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Info
        Column(
            modifier = Modifier.height(36.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .height(17.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(ButtonGray)
                    .padding(horizontal = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = HomeNTheme.typography.suitBold.toSpanStyle()) {
                            append(
                                infoFormat.replace("n", assignment.point.toString())
                                    .replace("s", assignment.weekdayLabel)
                            )
                        }
                        append(diffFormat.replace("s", assignment.difficultyLabel))
                    },
                    fontSize = 10.sp,
                    color = Color.Black,
                    style = HomeNTheme.typography.suitRegular
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = assignment.choreName,
                style = HomeNTheme.typography.suitBold,
                fontSize = 13.sp,
                color = Color.Black,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Checkbox
        Icon(
            painter = painterResource(Res.drawable.checkbox_icon),
            contentDescription = "chore help checkbox",
            modifier = Modifier.size(17.dp),
            tint = if (isSelected) Color.Black else GrayE7
        )
    }
}
