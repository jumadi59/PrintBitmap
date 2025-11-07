package com.lib.app;

import com.lib.print.Print;
import com.lib.print.component.Align;
import com.lib.print.component.LayoutAbsolute;
import com.lib.print.component.PrintImage;
import com.lib.print.component.PrintText;

/**
 * Created by Anonim date on 01/03/2023.
 * Bengkulu, Indonesia.
 * Copyright (c) Company. All rights reserved.
 **/
public class PrintTamplate {

    void tamplate() {
        new Print()
                .add(new PrintText("Test"))
                .add(new PrintImage<>("", Align.CENTER, 200))
                .add(new LayoutAbsolute()).build();
    }
}
