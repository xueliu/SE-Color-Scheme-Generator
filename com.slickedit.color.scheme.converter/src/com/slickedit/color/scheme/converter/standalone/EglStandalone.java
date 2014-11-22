package com.slickedit.color.scheme.converter.standalone;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplateFactoryModuleAdapter;
import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.models.IModel;

/**
 * This example demonstrates using the Epsilon Generation Language, the M2T
 * language of Epsilon, in a stand-alone manner
 * 
 * @author Xue Liu
 */
public class EglStandalone extends EpsilonStandalone {

	public static void main(String[] args) throws Exception {
		new EglStandalone().execute();
	}

	@Override
	public IEolExecutableModule createModule() {
		return new EglTemplateFactoryModuleAdapter(
				new EglFileGeneratingTemplateFactory());
	}

	@Override
	public List<IModel> getModels() throws Exception {
		List<IModel> models = new ArrayList<IModel>();
		models.add(createPlainXmlModel(
				"EclipseColorSchemeXML",
				"E:/cloudat/Training/workspaces/Xue_Liu/com.slickedit.color.scheme.converter/eclipse_color_scheme/theme-1013.xml",
				true, false));
		return models;
	}

	@Override
	public String getSource() throws Exception {
		return "/egl/main.egl";
	}

	@Override
	public void postProcess() {
		System.out.println(result);
	}

}
