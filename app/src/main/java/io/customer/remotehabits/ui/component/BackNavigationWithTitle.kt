package io.customer.remotehabits.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.customer.remotehabits.R
import io.customer.remotehabits.ui.theme.RHTheme

@Composable
fun BackNavigationWithTitle(
    title: String,
    onBackPressed: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = stringResource(id = R.string.back),
            colorFilter = ColorFilter.tint(color = RHTheme.colors.textPrimary),
            modifier = Modifier.clickable {
                onBackPressed.invoke()
            }
        )
        Text(
            text = title,
            style = RHTheme.typography.h2,
            color = RHTheme.colors.textPrimary
        )
    }
}
