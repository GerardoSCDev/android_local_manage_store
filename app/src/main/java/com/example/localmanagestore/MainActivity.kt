package com.example.localmanagestore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.localmanagestore.CommonUtils.Utils.PreferencesManager.PreferencesManager
import com.example.localmanagestore.Modules.StartApp.Screens.StartAppScreen
import com.example.localmanagestore.Modules.Inventory.Screens.InventoryScreen

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {

            val currentContext = LocalContext.current
            val preferencesManager = remember { PreferencesManager(currentContext) }
            val isFirstTime = remember { mutableStateOf(preferencesManager.getData("isFirstTimeKey", "true")) }
            if (isFirstTime.value.toBoolean()) {
                StartAppScreen()
            } else {
                Scaffold (
//                bottomBar = { AppButtonNavBar() }
                )
                {
                    InventoryScreen()
                }
            }

        }
    }
}

data class ModalPrivacyStrings (
    var privacyTitle: String,
    val privacyCheckMessage: String,
    val generalDetail:String,
    val privacyButtonTitle: String
)

data class PrivacyIndex (
    val title: String,
    val subtitles: List<DescPrivacyString>
)

data class DescPrivacyString(
    val textSubTitle: String,
    val desc: String
)

val modalPrivacyStrings: ModalPrivacyStrings = ModalPrivacyStrings(
    privacyTitle = "Politicas de privacidad",
    privacyCheckMessage = "He leido y acepto la política de privacidad",
    generalDetail = "Última actualización: 29/11/2023\n\nEsta Política de Privacidad describe cómo se recopilan, utilizan y comparten los datos cuando utilizas nuestra aplicación móvil miTiendita.",
    privacyButtonTitle = "Continuar"
)

val privacyIndex: List<PrivacyIndex> = listOf(
    PrivacyIndex(
        title = "\nInformación que recopilamos",
        subtitles = listOf(
            DescPrivacyString(
                textSubTitle = "\nDatos de la cámara: ",
                desc = "\nLa aplicación accede a la cámara de tu dispositivo para escanear códigos de barras y tomar fotos de productos. Estas imágenes se utilizan únicamente con el propósito de identificar y almacenar información sobre los productos en tu inventario."
            ),
            DescPrivacyString(
                textSubTitle = "\nAcceso a la galería de fotos: ",
                desc = "\nTambién podemos solicitar acceso a las fotos almacenadas en tu dispositivo para permitirte identificar y asociar productos en tu inventario con mayor facilidad. Estas imágenes se utilizan exclusivamente para el propósito de identificación de productos y no se comparten con terceros sin tu consentimiento."
            )
        )
    ),


    PrivacyIndex(
        title = "\nUso de la información",
        subtitles = listOf(
            DescPrivacyString(
                textSubTitle = "\nIdentificación de productos: ",
                desc = "\nLas imágenes capturadas se utilizan para identificar y asociar los productos escaneados con su información correspondiente en el inventario."
            ),
            DescPrivacyString(
                textSubTitle = "\nMejora de la aplicación: ",
                desc = "\nPodemos utilizar información anónima y agregada para mejorar la funcionalidad y la experiencia del usuario en la aplicación."
            ),
            //TODO: When add ads to app and deploy to store
//            DescPrivacyString(
//                textSubTitle = "\nPublicidad: ",
//                desc = "\nNuestra aplicación puede incluir anuncios proporcionados por terceros. Para mostrar publicidad relevante, estos terceros pueden recopilar y utilizar cierta información sobre tu dispositivo y tus hábitos de uso de la aplicación. No compartimos información personalmente identificable con estos terceros sin tu consentimiento. La publicidad se utiliza para financiar y mantener el servicio gratuito de la aplicación."
//            )
        )
    ),


    PrivacyIndex(
        title = "\nCompartir información",
        subtitles = listOf(
            DescPrivacyString(
                textSubTitle = "\nNo compartimos información personalmente identificable: ",
                desc = "\nLas imágenes capturadas con la cámara o accedidas desde la galería de fotos no se comparten con terceros a menos que sea necesario para el funcionamiento de la aplicación o con tu consentimiento explícito."
            ),
        )
    ),


    PrivacyIndex(
        title = "\nSeguridad de los datos",
        subtitles = listOf(
            DescPrivacyString(
                textSubTitle = "\nSeguridad de la información: ",
                desc = "\nTomamos medidas de seguridad adecuadas para proteger los datos capturados por la aplicación, incluyendo medidas físicas, técnicas y administrativas para proteger contra el acceso no autorizado, la alteración o la destrucción de la información."
            ),
        )
    ),


    PrivacyIndex(
        title = "\nTus derechos",
        subtitles = listOf(
            DescPrivacyString(
                textSubTitle = "\nAcceso y control de tus datos: ",
                desc = "\nPuedes acceder, rectificar o eliminar los datos capturados por la aplicación en cualquier momento a través de la configuración de la aplicación."
            ),
        )
    ),


    PrivacyIndex(
        title = "\nCambios en la Política de Privacidad",
        subtitles = listOf(
            DescPrivacyString(
                textSubTitle = "\nActualizaciones: ",
                desc = "\nNos reservamos el derecho de actualizar esta Política de Privacidad en cualquier momento. Te recomendamos revisar esta política periódicamente para estar informado sobre cómo protegemos tu información."
            ),
        )
    ),


    PrivacyIndex(
        title = "",
        subtitles = listOf(
            DescPrivacyString(
                textSubTitle = "",
                desc = "Nos reservamos el derecho de actualizar esta Política de Privacidad en cualquier momento. Te recomendamos revisar esta política periódicamente para estar informado sobre cómo protegemos tu información."
            ),
        )
    ),

)
