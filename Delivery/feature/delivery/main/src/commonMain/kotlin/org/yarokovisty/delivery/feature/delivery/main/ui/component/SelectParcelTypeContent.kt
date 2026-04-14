package org.yarokovisty.delivery.feature.delivery.main.ui.component

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
import delivery.feature.delivery.main.generated.resources.Res
import delivery.feature.delivery.main.generated.resources.parcel_type_title
import delivery.feature.delivery.main.generated.resources.parcel_type_value
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.common.delivery.parcel.domain.entity.PackageType
import org.yarokovisty.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.text.Paragraph16Regular
import org.yarokovisty.delivery.design.uikit.text.TitleH3

@Composable
internal fun SelectParcelTypeContent(
    parcelInfoList: List<ParcelInfo>,
    onSelect: (ParcelInfo) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            TitleH3(
                text = stringResource(Res.string.parcel_type_title),
                color = DeliveryTheme.colorScheme.textPrimary,
                modifier = Modifier.padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp)
            )
        }

        items(parcelInfoList, key = { it.id }) { parcelType ->
            _root_ide_package_.org.yarokovisty.delivery.feature.delivery.main.ui.component.ParcelTypeItem(
                parcelInfo = parcelType,
                onClick = { onSelect(parcelType) }
            )
        }
    }
}

@Composable
private fun ParcelTypeItem(
    parcelInfo: ParcelInfo,
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
            parcelInfo.name,
            parcelInfo.length,
            parcelInfo.width,
            parcelInfo.height
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
    val parcelInfos = listOf(
        ParcelInfo(
            id = "envelope",
            type = PackageType.ENVELOPE,
            name = "Конверт",
            length = 1,
            width = 1,
            height = 1,
            weight = 1,
        ),
        ParcelInfo(
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
        _root_ide_package_.org.yarokovisty.delivery.feature.delivery.main.ui.component.SelectParcelTypeContent(
            parcelInfos,
            onSelect = {}
        )
    }
}
