package com.slickedit.color.scheme.converter.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplateFactoryModuleAdapter;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.models.IModel;

import com.slickedit.color.scheme.converter.standalone.EpsilonStandalone;

public class EglHandler extends EpsilonStandalone {
	private String xmlFilePath;
	private String xmlFileFolder;
	
	public EglHandler(String xmlFilePath, String xmlFileFolder){
		this.xmlFilePath = xmlFilePath;
		this.xmlFileFolder = xmlFileFolder;
	}
	
	@Override
	public IEolExecutableModule createModule() {
		EglFileGeneratingTemplateFactory egltemplfactory = new EglFileGeneratingTemplateFactory();
		if (null == xmlFileFolder)
			try {
				egltemplfactory.setOutputRoot("../slickedit_color_scheme");
			} catch (EglRuntimeException e) {
				e.printStackTrace();
			}
		else
			try {
				egltemplfactory.setOutputRoot(xmlFileFolder);
			} catch (EglRuntimeException e) {
				e.printStackTrace();
			}
		return new EglTemplateFactoryModuleAdapter(egltemplfactory);
	}

	@Override
	public List<IModel> getModels() throws Exception {
		List<IModel> models = new ArrayList<IModel>();
		models.add(createPlainXmlModel("EclipseColorSchemeXML", xmlFilePath,
				true, false));
		return models;
	}

	@Override
	public String getSource() throws Exception {
		return "/egl/main.egl";
	}

	@Override
	public void postProcess() {
		
	}
}
