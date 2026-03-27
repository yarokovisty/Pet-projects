package org.yarokovisty.delivery.feature.delivery.main.impl.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import delivery.feature.delivery.main.impl.generated.resources.Res
import delivery.feature.delivery.main.impl.generated.resources.parcel_type_title
import delivery.feature.delivery.main.impl.generated.resources.parcel_type_value
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.Paragraph16Regular
import org.yarokovisty.delivery.design.uikit.TitleH3
import org.yarokovisty.delivery.feature.delivery.main.api.domain.entity.PackageType
import org.yarokovisty.delivery.feature.delivery.main.api.domain.entity.ParcelType

@Composable
internal fun SelectParcelTypeContent(
    parcelTypes: List<ParcelType>,
    onSelect: (ParcelType) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            TitleH3(
                text = stringResource(Res.string.parcel_type_title),
                color = DeliveryTheme.colorScheme.textPrimary,
                modifier = Modifier.padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp)
            )
        }

        items(parcelTypes, key = { it.id }) { parcelType ->
            ParcelTypeItem(
                parcelType = parcelType,
                onClick = { onSelect(parcelType) }
            )
        }
    }
}

@Composable
private fun ParcelTypeItem(
    parcelType: ParcelType,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
    ) {
        val text = stringResource(
            Res.string.parcel_type_value,
            parcelType.name,
            parcelType.length,
            parcelType.width,
            parcelType.height
        )

        Paragraph16Regular(
            text = text,
            color = DeliveryTheme.colorScheme.textPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectParcelTypeContentPreview() {
    val parcelTypes = listOf(
        ParcelType(
            id = "envelope",
            type = PackageType.ENVELOPE,
            name = "Конверт",
            length = 1,
            width = 1,
            height = 1,
            weight = 1,
        ),
        ParcelType(
            id = "box-s",
            type = PackageType.BOX_S,
            name = "Коробка S",
            length = 2,
            width = 2,
            height = 2,
            weight = 2,
        )
    )

    DeliveryTheme {
        SelectParcelTypeContent(parcelTypes, onSelect = {})
    }
}
