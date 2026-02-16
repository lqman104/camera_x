package com.luqman.android.camera.image_capture.ui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagePreviewBottomSheet(
    modifier: Modifier = Modifier,
    state: SheetState,
    content: Uri,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    ModalBottomSheet(
        sheetState = state,
        onDismissRequest = onDismiss,
        modifier = modifier
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
    ) {
        Column {
            IconButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp),
                onClick = onDismiss
            ) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "icon close")
            }

            Image(bitmap = getCapturedImage(context, content).asImageBitmap(), contentDescription = "image preview")
        }
    }
}