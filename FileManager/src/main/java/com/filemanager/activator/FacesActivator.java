package com.filemanager.activator;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;

@ApplicationScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class FacesActivator {

	//Just to activate the JSF 2.3
	
	//This thread helped me out:
	//https://stackoverflow.com/questions/45682309/changing-faces-config-xml-from-2-2-to-2-3-causes-javax-el-propertynotfoundexcept
}
