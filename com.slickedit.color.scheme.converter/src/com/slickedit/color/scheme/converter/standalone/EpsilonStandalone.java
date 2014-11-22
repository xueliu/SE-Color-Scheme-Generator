package com.slickedit.color.scheme.converter.standalone;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.xml.XmlModel;
import org.eclipse.epsilon.emc.plainxml.PlainXmlModel;
import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;

public abstract class EpsilonStandalone {

	protected IEolExecutableModule module;

	protected Object result;

	public abstract IEolExecutableModule createModule();

	public abstract String getSource() throws Exception;

	public abstract List<IModel> getModels() throws Exception;

	public void postProcess() {
	};

	public void preProcess() {
	};
	
	public void execute() throws Exception {

		module = createModule();
		module.parse(getFile(getSource()));

		if (module.getParseProblems().size() > 0) {
			System.err.println("Parse errors occured...");
			for (ParseProblem problem : module.getParseProblems()) {
				System.err.println(problem.toString());
			}
			System.exit(-1);
		}

		for (IModel model : getModels()) {
			module.getContext().getModelRepository().addModel(model);
		}

		preProcess();
		result = execute(module);
		postProcess();

		module.getContext().getModelRepository().dispose();
	}

	protected Object execute(IEolExecutableModule module)
			throws EolRuntimeException {
		return module.execute();
	}

	protected EmfModel createEmfModel(String name, String model,
			String metamodel, boolean readOnLoad, boolean storeOnDisposal)
			throws EolModelLoadingException, URISyntaxException {
		EmfModel emfModel = new EmfModel();
		StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_NAME, name);
		properties.put(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI,
				getFile(metamodel).toURI().toString());
		properties.put(EmfModel.PROPERTY_MODEL_URI, getFile(model).toURI()
				.toString());
		properties.put(EmfModel.PROPERTY_READONLOAD, readOnLoad + "");
		properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, storeOnDisposal + "");
		emfModel.load(properties, null);
		return emfModel;
	}

	protected EmfModel createEmfModelByURI(String name, String model,
			String metamodel, boolean readOnLoad, boolean storeOnDisposal)
			throws EolModelLoadingException, URISyntaxException {
		EmfModel emfModel = new EmfModel();
		StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_NAME, name);
		properties.put(EmfModel.PROPERTY_METAMODEL_URI, metamodel);
		properties.put(EmfModel.PROPERTY_MODEL_URI, getFile(model).toURI()
				.toString());
		properties.put(EmfModel.PROPERTY_READONLOAD, readOnLoad + "");
		properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, storeOnDisposal + "");
		emfModel.load(properties, null);
		return emfModel;
	}

	protected XmlModel createXmlModel(String name, String modelfile,
			String xsdfile, boolean readOnLoad, boolean storeOnDisposal)
			throws EolModelLoadingException, URISyntaxException {
		XmlModel xmlModel = new XmlModel();
		StringProperties properties = new StringProperties();
		properties.put(XmlModel.PROPERTY_NAME, name);
		properties.put(XmlModel.PROPERTY_MODEL_FILE, modelfile);
		properties.put(XmlModel.PROPERTY_XSD_FILE, xsdfile);
		properties.put(XmlModel.PROPERTY_READONLOAD, readOnLoad + "");
		properties.put(XmlModel.PROPERTY_STOREONDISPOSAL, storeOnDisposal + "");
		xmlModel.load(properties, null);
		return xmlModel;
	}

	protected XmlModel createXmlModel(String name, String modelfile,
			boolean readOnLoad, boolean storeOnDisposal)
			throws EolModelLoadingException, URISyntaxException {
		XmlModel xmlModel = new XmlModel();
		StringProperties properties = new StringProperties();
		properties.put(XmlModel.PROPERTY_NAME, name);
		properties.put(XmlModel.PROPERTY_MODEL_FILE, modelfile);
		properties.put(XmlModel.PROPERTY_READONLOAD, readOnLoad + "");
		properties.put(XmlModel.PROPERTY_STOREONDISPOSAL, storeOnDisposal + "");
		xmlModel.load(properties, null);
		return xmlModel;
	}

	protected PlainXmlModel createPlainXmlModel(String name, String xmlfile,
			boolean readOnLoad, boolean storeOnDisposal)
			throws EolModelLoadingException, URISyntaxException {
		PlainXmlModel plainXmlModel = new PlainXmlModel();
		StringProperties properties = new StringProperties();
		properties.put(PlainXmlModel.PROPERTY_NAME, name);
		properties.put(PlainXmlModel.PROPERTY_FILE, xmlfile);
		properties.put(PlainXmlModel.PROPERTY_READONLOAD, readOnLoad + "");
		properties.put(PlainXmlModel.PROPERTY_STOREONDISPOSAL, storeOnDisposal
				+ "");
		plainXmlModel.load(properties, null);
		return plainXmlModel;
	}

	protected File getFile(String fileName) throws URISyntaxException,
			NullPointerException {

		URI binUri = EpsilonStandalone.class.getResource(fileName).toURI();
		return new File(binUri);
	}

	public File getFileInBundle(String fileName) throws URISyntaxException,
			IOException {;
		return new File(EpsilonStandalone.class.getResource(fileName).getFile());
	}

}