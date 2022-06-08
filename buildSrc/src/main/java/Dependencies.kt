object Version {
    const val kotlin = "1.5.21"
    const val android_support = "28.0.0"
    const val recyclerView = "1.2.0-alpha06"

    const val anko = "0.10.8"
    const val retrofit = "2.9.0"
    const val rxretrofit = "2.9.0"
    const val okhttp_logging = "4.9.1"
    const val chuck = "3.2.0"
    const val rxandroid = "2.1.1"
    const val rxkotlin = "2.4.0"
    const val glide = "4.13.0"
    const val browser = "1.2.0"
    const val multiDex = "2.0.1"
    const val rxJava = "2.2.19"
    const val playServiceBase = "17.1.0"

    const val swipeRefreshLayout = "1.1.0"
    const val desugar = "1.0.10"

    //Appsflyer
    const val appsflyer = "5.3.0"
    const val installReferrer = "2.1"

    //Firebase
    const val machineLearning = "23.0.0"
    const val mlLabel = "20.0.0"
    const val iid = "20.0.0"
    const val analytics = "17.5.0"
    const val performance = "19.0.10"
    const val googleAnalytics = "10.2.4"
    const val crashlytics = "17.2.1"
    const val remoteConfig = "19.0.3"
    const val storage = "19.1.0"
    const val dynamicLinks = "19.1.1"

    //testing
    const val jUnit = "4.12"
    const val jUnitExt = "1.1.3"
    const val testRunner = "1.2.0-beta01"
    const val espresso = "3.4.0"
    const val mockito = "2.8.47"
    const val mockKotlin = "2.1.0"
    const val mockTestRunner = "0.3.1"

    //play Service
    const val playServiceAuth = "17.0.0"
    const val gmsLocation = "17.0.0"
    const val places = "2.0.0"

    //Calligraphy
    const val calligraphy = "3.1.1"
    const val viewPump = "2.0.3"

    //OgacleJapan
    const val smartTab = "2.0.0@aar"
    const val smartTabUtils = "2.0.0@aar"

    //CarouselView
    const val carouselView = "0.1.6"

    //Wizzard
    const val wizzard = "1.0.2"
    const val swipeView = "1.1.0"

    //DISCRETE
    const val discreteSlider = "1.1.2"

    //StickyListView
    const val sticky = "1.0.1"
    const val stickyHeader = "1.0.2"

    //Facebook
    const val shimmer = "0.5.0"
    const val ogMapper = "1.0.0"

    //Lottie
    const val lottie = "3.4.0"

    //Chart
    const val philJay = "v3.1.0"

    //JODA
    const val jodaTime = "2.10.9.1"

    //Locale
    const val locale = "1.0.2"
    const val cropper = "2.2.4"
    const val imagePicker = "2.1.0"
    const val rxPaparazzo = "0.6.1-2.x"
    const val rvAnimator = "3.0.0"
    const val spotlight = "1.1"
    const val konfetti = "1.2.2"

    const val crisp = "0.1.15"

    //Youtube Player
    const val youtubePlayer = "10.0.5"

    //TINDER
    const val scarlet = "0.1.10"
    const val moshi = "1.8.0"

    //AirBnB
    const val paris = "1.4.0"

    //CAMERA
    const val cameraX = "1.0.2"
    const val cameraView = "2.7.0"

    //RICHTEXT
    const val richText = "6.0.25"

    //debug
    const val leakCanary = "2.4"

    //LUCU-LUCU
    const val bioMetric = "1.1.0"
    const val signature = "1.3.1"

    //RESTRING
    const val restring = "4.0.3"
    const val reword = "1.1.0"
    const val appLocale = "1.1.0"
    const val restring2 = "1.0.0"
}

object Analytics {
    const val cleverTap = "com.clevertap.android:clevertap-android-sdk:4.1.1"
    const val appsflyer = "com.appsflyer:af-android-sdk:${Version.appsflyer}"
    const val af_installReferrer =
        "com.android.installreferrer:installreferrer:${Version.installReferrer}"
    const val google = "com.google.android.gms:play-services-analytics:${Version.googleAnalytics}"
}

object CameraX {
    const val cameraCore = "androidx.camera:camera-core:${Version.cameraX}"
    const val cameraX = "androidx.camera:camera-camera2:${Version.cameraX}"
    const val cameraView = "androidx.camera:camera-view:1.0.0-alpha24"
    const val cameraExt = "androidx.camera:camera-extensions:1.0.0-alpha24"
    const val cameraLifecycle = "androidx.camera:camera-lifecycle:${Version.cameraX}"
}

object CameraView {
    const val cameraView = "com.otaliastudios:cameraview:${Version.cameraView}"
}

object Chat {
    const val crisp = "im.crisp:crisp-sdk:${Version.crisp}"
}

object Joda {
    const val time = "net.danlew:android.joda:${Version.jodaTime}"
}

object Animation {
    const val recyclerView = "jp.wasabeef:recyclerview-animators:${Version.rvAnimator}"
}

object RichText {
    const val richText = "io.square1:richtext:${Version.richText}"
}

object Helper {
    const val imagePicker =
        "com.github.esafirm.android-image-picker:imagepicker:${Version.imagePicker}"
    const val cropper = "com.github.yalantis:ucrop:${Version.cropper}"
    const val openCSV = "com.opencsv:opencsv:5.3"
}

object Facebook {
    //https://github.com/iumehara/ogmapper
    const val oghMaper = "io.umehara:ogmapper:${Version.ogMapper}"
}

object Layout {
    const val stickyScroll = "com.github.didikk:sticky-nestedscrollview:${Version.sticky}"
    const val shimmer = "com.facebook.shimmer:shimmer:${Version.shimmer}"
    const val carouselView = "com.synnapps:carouselview:${Version.carouselView}"
    const val smartTab = "com.ogaclejapan.smarttablayout:library:${Version.smartTab}"
    const val smartTabUtils = "com.ogaclejapan.smarttablayout:utils-v4:${Version.smartTabUtils}"
    const val chart = "com.github.PhilJay:MPAndroidChart:${Version.philJay}"
    const val lottie = "com.airbnb.android:lottie:${Version.lottie}"
    const val wizzard = "com.github.TayfunCesur:Stepper:${Version.wizzard}"
    const val slider = "com.github.hearsilent:DiscreteSlider:${Version.discreteSlider}"
    const val spotlight = "com.github.mreram:showcaseview:${Version.spotlight}"
    const val swipeView = "ru.rambler.android:swipe-layout:${Version.swipeView}"
    const val stickyHeader = "com.github.amarjain07:StickyScrollView:${Version.stickyHeader}"
    const val paris = "com.airbnb.android:paris:${Version.paris}"
    const val konfetti = "nl.dionsegijn:konfetti:${Version.konfetti}"
}

object Language {
    const val changer = "com.zeugmasolutions.localehelper:locale-helper-android:${Version.locale}"
}

object Calligraphy {
    const val calligraphy = "io.github.inflationx:calligraphy3:${Version.calligraphy}"
    const val viewPump = "io.github.inflationx:viewpump:${Version.viewPump}"
}

object Dependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"
}

object AndroidSupport {
    const val support = "com.android.support:support-v4:${Version.android_support}"
    const val playServiceBase =
        "com.google.android.gms:play-services-base:${Version.playServiceBase}"
}

object Firebase {
    const val ml = "com.google.firebase:firebase-ml-vision:${Version.machineLearning}"
    const val mlLabel =
        "com.google.firebase:firebase-ml-vision-image-label-model:${Version.mlLabel}"
    const val messaging = "com.google.firebase:firebase-messaging:20.2.4"
    const val iid = "com.google.firebase:firebase-iid:${Version.iid}"
    const val analytics = "com.google.firebase:firebase-analytics:${Version.analytics}"
    const val performance = "com.google.firebase:firebase-perf:${Version.performance}"
    const val crashlytics = "com.google.firebase:firebase-crashlytics:${Version.crashlytics}"
    const val remoteConfig = "com.google.firebase:firebase-config:${Version.remoteConfig}"
    const val storage = "com.google.firebase:firebase-storage:${Version.storage}"
    const val dynamicLinks = "com.google.firebase:firebase-dynamic-links:${Version.dynamicLinks}"
}

object KotlinCoroutine {
    private const val version = "1.4.2"
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    const val rx2 = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:$version"
}

object AndroidX {
    private const val nav_version = "2.3.5"
    const val appCompat = "androidx.appcompat:appcompat:1.4.1"
    const val androidCoreKTX = "androidx.core:core-ktx:1.7.0"
    const val androidCore = "androidx.core:core-ktx:1.7.0"
    const val fragment = "androidx.fragment:fragment:1.2.5"
    const val fragmentKTX = "androidx.fragment:fragment-ktx:1.2.5"
    const val activity = "androidx.androidx.activity:activity:1.2.0-rc01"
    const val activityKTX = "androidx.activity:activity-ktx:1.2.0-rc01"
    const val constraint = "androidx.constraintlayout:constraintlayout:2.1.3"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3"
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$nav_version"
    const val navigationUI = "androidx.navigation:navigation-ui-ktx:$nav_version"
    const val legacySupport = "androidx.legacy:legacy-support-v4:1.0.0"

    const val recyclerView = "androidx.recyclerview:recyclerview:${Version.recyclerView}"
    const val material = "com.google.android.material:material:1.5.0"
    const val browser = "androidx.browser:browser:${Version.browser}"
    const val multiDex = "androidx.multidex:multidex:${Version.multiDex}"
    const val viewPager = "androidx.viewpager:viewpager:1.0.0"
    const val viewPager2 = "androidx.viewpager2:viewpager2:1.1.0-alpha01"
    const val swipeRefreshLayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Version.swipeRefreshLayout}"
    const val desugar = "com.android.tools:desugar_jdk_libs:${Version.desugar}"
    const val startup = "androidx.startup:startup-runtime:1.1.0-alpha01"
    const val preference = "androidx.preference:preference:1.1.1"
    const val preferenceKTX = "androidx.preference:preference-ktx:1.1.1"
}

object AndroidLifecycle {
    private const val lifecycleVersion = "2.3.1"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
    const val extensions = "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion"
    const val compiler = "android.arch.lifecycle:compiler:$lifecycleVersion"
}

object Room {
    private const val version = "2.3.0"
    const val ktx = "androidx.room:room-ktx:$version"
    const val runtime = "androidx.room:room-runtime:$version"
    const val compiler = "androidx.room:room-compiler:$version"
    const val testing = "androidx.room:room-testing:$version"
    const val rx = "android.arch.persistence.room:rxjava2:$version"
}

object Hilt {
    private const val version = "2.38.1"
    const val plugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
    const val core = "com.google.dagger:hilt-android:$version"
    const val compiler = "com.google.dagger:hilt-android-compiler:$version"
}

object
Scarlet {
    const val scarlet = "com.tinder.scarlet:scarlet:${Version.scarlet}"
    const val okHttp = "com.tinder.scarlet:websocket-okhttp:${Version.scarlet}"
    const val rxJava2Adapter = "com.tinder.scarlet:stream-adapter-rxjava2:${Version.scarlet}"
    const val gsonConverter = "com.tinder.scarlet:message-adapter-gson:${Version.scarlet}"
    const val lifeCycleAndroid = "com.tinder.scarlet:lifecycle-android:${Version.scarlet}"
    const val moshi = "com.squareup.moshi:moshi-kotlin:${Version.moshi}"
}

object Youtube {
    const val player =
        "com.pierfrancescosoffritti.androidyoutubeplayer:core:${Version.youtubePlayer}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Version.retrofit}"
    const val scalarConverter = "com.squareup.retrofit2:converter-scalars:${Version.retrofit}"
    const val rxAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Version.rxretrofit}"
    const val moshi = "com.squareup.moshi:moshi:${Version.moshi}"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Version.rxretrofit}"
    const val rx3Adapter = "com.github.akarnokd:rxjava3-retrofit-adapter:${Version.rxJava}"
    const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:${Version.okhttp_logging}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Version.okhttp_logging}"
    const val chucker = "com.github.ChuckerTeam.Chucker:library:${Version.chuck}"
}

object Reactivex {
    const val android = "io.reactivex.rxjava2:rxandroid:${Version.rxandroid}"
    const val kotlin = "io.reactivex.rxjava2:rxkotlin:${Version.rxkotlin}"
    const val paparazzo = "com.github.miguelbcr:RxPaparazzo:${Version.rxPaparazzo}"
}

object GMS {
    const val auth = "com.google.android.gms:play-services-auth:${Version.playServiceAuth}"
    const val location = "com.google.android.gms:play-services-location:${Version.gmsLocation}"
    const val places = "com.google.android.libraries.places:places-compat:${Version.places}"
}

object Gson {
    const val gson = "com.google.code.gson:gson:2.8.7"
}

object ExifInterface {
    const val exifInterface = "androidx.exifinterface:exifinterface:1.3.2"
}

object Auth {
    const val jwt = "com.auth0.android:jwtdecode:2.0.0"
}

object Glide {
    const val glide = "com.github.bumptech.glide:glide:${Version.glide}"
    const val compiler = "com.github.bumptech.glide:compiler:${Version.glide}"
}

object RXJava {
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Version.rxJava}"
}

object Dagger {
    private const val version = "2.29.1"
    const val dagger = "com.google.dagger:dagger:$version"
    const val android = "com.google.dagger:dagger-android:$version"
    const val androidSupport = "com.google.dagger:dagger-android-support:$version"
    const val compiler = "com.google.dagger:dagger-compiler:$version"
    const val processor = "com.google.dagger:dagger-android-processor:$version"
}

object Utils {
    const val bioMetric = "androidx.biometric:biometric:${Version.bioMetric}"
    const val signature = "com.github.gcacace:signature-pad:${Version.signature}"
}

object DebugTools {
    const val canary = "com.squareup.leakcanary:leakcanary-android:${Version.leakCanary}"
}

object Anko {
    const val anko = "org.jetbrains.anko:anko:${Version.anko}"
    const val common = "org.jetbrains.anko:anko-commons:${Version.anko}"
}

object Testing {
    const val jUnit = "junit:junit:${Version.jUnit}"
    const val jUnitExt = "androidx.test.ext:junit:1.1.3"
    const val testRunner = "androidx.test:runner:${Version.testRunner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Version.espresso}"
    const val mockito = "org.mockito:mockito-inline:${Version.mockito}"
    const val mockKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Version.mockKotlin}"
    const val mockKtRunner = "de.jodamob.kotlin:kotlin-runner-junit4:${Version.mockTestRunner}"
}

object Restring {
    const val restring = "dev.b3nedikt.restring:restring:${Version.restring}"
    const val restring2 = "com.ice.restring:restring:${Version.restring2}"
    const val reword = "dev.b3nedikt.reword:reword:${Version.reword}"
    const val appLocale = "dev.b3nedikt.applocale:applocale:${Version.appLocale}"
}

object PhotoView {
    const val photoView = "com.github.chrisbanes:PhotoView:2.3.0"
}

object TensorFlow {
    private const val version = "2.4.0"
    const val tensorFlow = "org.tensorflow:tensorflow-lite:$version"
    const val tensorFlowGPU = "org.tensorflow:tensorflow-lite-gpu:$version"
    const val tensorFlowSupport = "org.tensorflow:tensorflow-lite-support:0.1.0"
    const val tensorFlowMetadata = "org.tensorflow:tensorflow-lite-metadata:0.0.0-nightly"
}

object Barcode {
    const val zxing = "com.google.zxing:core:3.4.0"
    const val mlkit = "com.google.mlkit:barcode-scanning:16.2.0"
}

object UsbSerial {
    const val felHR85 = "com.github.felHR85:UsbSerial:6.1.0"
}

object CropImage {
    const val cropper = "com.edmodo:cropper:1.0.1"
    const val canHub = "com.github.CanHub:Android-Image-Cropper:3.2.0"
}

object Log {
    const val logger = "com.orhanobut:logger:2.2.0"
}

object MLKit {
    const val faceDetection = "com.google.mlkit:face-detection:16.1.2"
}

object DataStore {
    const val datastore = "androidx.datastore:datastore:1.0.0"
    const val datastore_preference = "androidx.datastore:datastore-preferences:1.0.0"
    const val datastore_rxjava2 = "androidx.datastore:datastore-rxjava2:1.0.0"
    const val datastore_rxjava3 = "androidx.datastore:datastore-rxjava3:1.0.0"
    const val datastore_core = "androidx.datastore:datastore-core:1.0.0"
}


object Download {
    const val ktor = "io.ktor:ktor-client-android:1.2.5"
}

object OneUI {
    const val yanndroid = "io.github.yanndroid:oneui:2.4.0"
    const val appcombat = "io.github.oneuiproject.sesl:appcompat:1.0.0"
    const val coordinatorlayout = "io.github.oneuiproject.sesl:coordinatorlayout:1.0.0"
    const val drawerlayout = "io.github.oneuiproject.sesl:drawerlayout:1.0.0"
    const val preference = "io.github.oneuiproject.sesl:preference:1.0.0"
    const val recyclerview = "io.github.oneuiproject.sesl:recyclerview:1.0.0"
    const val swiperefreshlayout = "io.github.oneuiproject.sesl:swiperefreshlayout:1.0.0"
    const val viewpager = "io.github.oneuiproject.sesl:viewpager:1.0.0"
    const val viewpager2 = "io.github.oneuiproject.sesl:viewpager2:1.0.0"
    const val material = "io.github.oneuiproject.sesl:material:1.0.0"
    const val apppickerview = "io.github.oneuiproject.sesl:apppickerview:1.0.0"
    const val indexscroll = "io.github.oneuiproject.sesl:indexscroll:1.0.0"
    const val pickerBasic = "io.github.oneuiproject.sesl:picker-basic:1.0.0"
    const val pickerColor = "io.github.oneuiproject.sesl:picker-color:1.0.0"
}