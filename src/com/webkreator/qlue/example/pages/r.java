package com.webkreator.qlue.example.pages;

import com.webkreator.qlue.Page;
import com.webkreator.qlue.view.RedirectView;
import com.webkreator.qlue.view.View;

public class r extends Page {

    @Override
    public View onGet() throws Exception {
        return new RedirectView("/#123");
    }
}
