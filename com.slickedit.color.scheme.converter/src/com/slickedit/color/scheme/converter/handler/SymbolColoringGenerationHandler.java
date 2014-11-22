package com.slickedit.color.scheme.converter.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplateFactoryModuleAdapter;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.models.IModel;

import com.slickedit.color.scheme.converter.standalone.EpsilonStandalone;

public class SymbolColoringGenerationHandler extends EpsilonStandalone {
	private String xmlFilePath;
	final private String genXmlFileName = "SymbolColoring.xml";
	private String xmlFileFolder;
	private String genXmlFilePath;
	
	public SymbolColoringGenerationHandler(File xmlfile) {
		this.xmlFilePath	= xmlfile.getAbsolutePath();
		this.xmlFileFolder	= xmlfile.getParent();
		this.genXmlFilePath = xmlFileFolder + File.separator + genXmlFileName;
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
		// source XML file
		models.add(createPlainXmlModel("EclipseColorSchemeXML", xmlFilePath,
				true, false));
		// destination XML file
		models.add(createPlainXmlModel("SymbolColoring", genXmlFilePath,
				false, true));
		
		return models;
	}

	@Override
	public String getSource() throws Exception {
		return "/egl/SymbolColoringGeneration.egl";
	}

	@Override
	public void postProcess() {
		
	}
}

