package org.yarokovisty.delivery.feature.delivery.main.ui.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.design.uikit.screen.BottomSheetScreen
import org.yarokovisty.delivery.feature.delivery.main.presentation.intent.DeliveryMainIntent
import org.yarokovisty.delivery.feature.delivery.main.ui.component.SelectParcelTypeContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SelectParcelTypeScreen(
    visible: Boolean,
    parcelInfoList: List<ParcelInfo>,
    onIntent: (DeliveryMainIntent) -> Unit,
) {
    BottomSheetScreen(
        visible = visible,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        onDismissRequest = {
            onIntent(DeliveryMainIntent.CloseParcelTypeScreen)
        }
    ) {
        SelectParcelTypeContent(
            parcelInfoList = parcelInfoList,
            onSelect = { parcelType ->
                onIntent(DeliveryMainIntent.SelectParcelType(parcelType))
            }
        )
    }
}
