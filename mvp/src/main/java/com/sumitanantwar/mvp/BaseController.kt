package com.sumitanantwar.mvp

import android.os.Bundle
import com.bluelinelabs.conductor.Controller

abstract class BaseController : Controller {

    //======= Initializers =======
    protected constructor() : super()
    protected constructor(args: Bundle) : super(args)


}