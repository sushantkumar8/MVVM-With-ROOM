package com.assignment.fluper.utils

import com.assignment.fluper.interfaces.IProductActions

/**
 *  @Singleton: It is the Singleton class,
 *
 *  @see IProductListener
 *
 *  @author  Sushant Kumar
 */
object Singleton {
    var IProductListener: IProductActions ?= null;
}