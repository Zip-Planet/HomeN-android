package com.devndev.homen.ui.main.home.main

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.core.domain.model.chore.ChoreCategory
import com.devndev.homen.core.domain.model.home.AssignmentItem
import com.devndev.homen.ui.common.resource
import com.devndev.homen.ui.theme.BackgroundGray
import com.devndev.homen.ui.theme.Blue4
import com.devndev.homen.ui.theme.ButtonGray
import com.devndev.homen.ui.theme.GrayE7
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.checkbox_icon
import homen.composeapp.generated.resources.chore_info_difficulty
import homen.composeapp.generated.resources.chore_info_point_days
import homen.composeapp.generated.resources.home_assignment_complete_badge_label
import homen.composeapp.generated.resources.home_assignment_incomplete_badge_label
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeAssignmentItem(
    assignment: AssignmentItem,
    isMine: Boolean,
    onItemClick: (Boolean) -> Unit = {}
) {
    val choreResource = ChoreCategory.fromId(assignment.category).resource
    val infoFormat = stringResource(Res.string.chore_info_point_days)
    val diffFormat = stringResource(Res.string.chore_info_difficulty)
    val boxColor = if (assignment.isCompleted) ButtonGray else Blue4
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                if (isMine) {
                    onItemClick(assignment.isCompleted)
                }
            }
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(BackgroundGray),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(22.dp),
                painter = painterResource(choreResource),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier.height(36.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .height(17.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(boxColor)
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

            Text(
                text = assignment.choreName,
                style = HomeNTheme.typography.suitBold,
                fontSize = 13.sp,
                color = Color.Black,
                textDecoration = if (assignment.isCompleted) TextDecoration.LineThrough else TextDecoration.None
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        if (isMine) {
            Icon(
                painter = painterResource(Res.drawable.checkbox_icon),
                modifier = Modifier
                    .size(17.dp),
                contentDescription = "package select checkbox",
                tint = if (assignment.isCompleted) Color.Black else GrayE7
            )
        } else {
            val (boxColor, textColor, labelRes) = if (assignment.isCompleted) {
                Triple(Color.Black, Color.White, Res.string.home_assignment_complete_badge_label)
            } else {
                Triple(ButtonGray, Color.Black, Res.string.home_assignment_incomplete_badge_label)
            }

            Box(
                modifier = Modifier
                    .height(17.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(boxColor)
                    .padding(horizontal = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(labelRes),
                    color = textColor,
                    style = HomeNTheme.typography.suitRegular,
                    fontSize = 10.sp
                )
            }
        }
    }
}
