package com.example.componentframe

import com.tencent.tinker.loader.app.TinkerApplication
import com.tencent.tinker.loader.shareutil.ShareConstants

class ComponentFrameApplication() : TinkerApplication(ShareConstants.TINKER_ENABLE_ALL, "com.example.componentframe.ComponentFrameApplicationLike",
        "com.tencent.tinker.loader.TinkerLoader", false)