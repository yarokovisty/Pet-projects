package org.yarokovisty.delivery.feature.delivery.main.impl.ui.screen

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.feature.delivery.main.api.domain.entity.ParcelInfo
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.intent.DeliveryMainIntent
import org.yarokovisty.delivery.feature.delivery.main.impl.ui.component.SelectParcelTypeContent
import org.yarokovisty.delivery.util.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SelectParcelTypeScreen(
    parcelInfos: List<ParcelInfo>,
    onIntent: (DeliveryMainIntent) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        sheetState = sheetState,
        containerColor = DeliveryTheme.colorScheme.bgPrimary,
        shape = RoundedCornerShape(16.dp),
        onDismissRequest = {
            onIntent(DeliveryMainIntent.CloseParcelTypeScreen)
        }
    ) {
        SelectParcelTypeContent(
            parcelInfos = parcelInfos,
            onSelect = { parcelType ->
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    onIntent(DeliveryMainIntent.SelectParcelType(parcelType))
                }
            }
        )
    }
}
