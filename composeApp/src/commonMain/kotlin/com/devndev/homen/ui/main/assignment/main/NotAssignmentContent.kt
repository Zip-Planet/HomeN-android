package com.devndev.homen.ui.main.assignment.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.assignment_add_chore_btn
import homen.composeapp.generated.resources.assignment_add_chore_msg
import homen.composeapp.generated.resources.assignment_add_chore_title
import homen.composeapp.generated.resources.clipboard_icon
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun NotAssignmentContent(
    icon: DrawableResource,
    title: String,
    message: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = HomeNTheme.dimensions.horizontalPadding)
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(10.dp))
            .padding(vertical = 20.dp, horizontal = 15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.5.dp)
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(22.dp)
            )

            Text(
                text = title,
                style = HomeNTheme.typography.suitExtraBold,
                color = Color.Black,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(13.dp))

        Text(
            text = message,
            style = HomeNTheme.typography.suitRegular,
            color = Color.Black,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        HomeNButton(
            text = buttonText,
            onClick = onClick
        )
    }
}

@Preview
@Composable
fun AddChoreContentPreview() {
    NotAssignmentContent(
        icon = Res.drawable.clipboard_icon,
        title = stringResource(Res.string.assignment_add_chore_title),
        message = stringResource(Res.string.assignment_add_chore_msg),
        buttonText = stringResource(Res.string.assignment_add_chore_btn),
    ) {

    }
}