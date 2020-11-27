/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.controllers;

import smartworkflow.dwfms.urifia.fmml.miu.controllers.util.ControllerModel;
import smartworkflow.dwfms.urifia.fmml.miu.models.StartupModel;

public class StartupController extends ControllerModel{

    public StartupController(StartupModel model) {
        super(model);
    }

    public void startupLoading() {
        ((StartupModel)model).startupLoading();
    }
}
