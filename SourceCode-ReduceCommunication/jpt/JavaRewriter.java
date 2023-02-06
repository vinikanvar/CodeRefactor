package jpt;

public class JavaRewriter {
//	// Logger
//	private static final Logger logger = LoggerFactory.getLogger(JavaRewriter.class);
//	
//	APIDiscoverer apiDiscoverer;
//	
//	public boolean rewrite(String sProjectRoot,
//			String[] definitionPaths,
//			String outputFolder,
//			APIDiscoverer apiDiscoverer)
//	{
//		logger.info("enter rewrite");
//		
//		this.apiDiscoverer = apiDiscoverer;
//		
//		Path pProjectRoot = Paths.get(sProjectRoot);
//		ProjectRoot projectRoot = new SymbolSolverCollectionStrategy().collect(pProjectRoot);
//		for (String directory: definitionPaths)
//		{
//			projectRoot.addSourceRoot(Paths.get(directory));
//			logger.info("directory=" + directory);
//		}
//		
//		/*
//        try {
//            List<File> files = Files.list(pProjectRoot)
//                                    .filter(Files::isRegularFile)
//                                    //.filter(path -> path.toString().endsWith(".java"))
//                                    .map(Path::toFile)
//                                    .collect(Collectors.toList());
//             
//            for (File file: files)
//            {
//            	logger.info("file=" + file.getName());
//            }
//            //files.forEach(System.out::println);
//        } catch (IOException e) {
//			e.printStackTrace();
//        }
//		*/
//        
//		for (SourceRoot srcRoot : projectRoot.getSourceRoots())
//		{
//			logger.info("srcRoot=" + srcRoot.toString());
//			try {
//				List<ParseResult<CompilationUnit>> results = srcRoot.tryToParse();
//				logger.info("results size " + results.size());
//				// Once for every java file in the project
//				for (ParseResult<CompilationUnit> parseResult : results) 
//					parseRewriteDump(parseResult, outputFolder);
//				
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		return true;
//	}
//	
//	private boolean parseRewriteDump(ParseResult<CompilationUnit> parseResult, String outputFolder)
//	{
//		Optional<CompilationUnit> cuParseResult = parseResult.getResult();
//		if (!cuParseResult.isPresent())
//		{
//			logger.info("parse result fail");
//			return false;
//		}
//		CompilationUnit cu = cuParseResult.get();
//		cu.getModule();
//		Optional<PackageDeclaration> opd = cu.getPackageDeclaration();
//		String pkgName = "";
//		String subpath = "";
//		if (opd.isPresent())
//		{
//			PackageDeclaration pd = opd.get();
//			pkgName = pd.getNameAsString();
//			logger.info("pkgName=" + pkgName);
//			subpath = pkgName.replace(".", "/");
//		}
//​
//		if (parseResult.isSuccessful())
//			logger.info("parse success for " + pkgName);
//		else
//		{
//			logger.info("parse fail for " + pkgName);
//			return false;
//		}
//		
//		rewriteCallerCallee(cu, outputFolder);
//		
//		dumpCode(cu, pkgName, outputFolder, subpath);
//		
//		return true;
//	}
//	
//	private void rewriteCallerCallee(CompilationUnit cu, String outputFolder)
//	{
//		MethodVisitor cv = new MethodVisitor(cu, apiDiscoverer);
//		logger.info("new MethodVisitor done");
//		cv.visit(cu, null);
//		logger.info("visit(CompilationUnit); done");
//		
//		importGsonPackage(cv, cu, outputFolder);
//		
//		logger.info("after visit cu = ");
//		logger.info(cu.toString());	
//	}
//	
//	private void dumpCode(CompilationUnit cu, String pkgName, String outputFolder, String subpath)
//	{
//		Storage diskFileForCU = cu.getStorage().get();
//		String directory = diskFileForCU.getDirectory().toString();
//		String fileName = diskFileForCU.getFileName();
//		logger.info("CU storage dir = " + directory);
//		logger.info("CU storage file name = " + fileName);
//		
//		ClassNameCollector classNameVisitor = new ClassNameCollector();
//		List<Integer> microServiceGroupNumbers = classNameVisitor.
//				getMicroServiceGroupNumberOfFile(cu, pkgName, apiDiscoverer);
//		if (microServiceGroupNumbers.size() > 1)
//			logger.info("file needs to be split into directories=" + microServiceGroupNumbers.size());
//	    for (Integer ms: microServiceGroupNumbers)
//	    {
//	    	logger.info("Microservice group number=" + ms);
//    		String msnumber = "";
//    		if (ms == null)
//    			logger.info("MS not found");
//    		else
//    			msnumber = ms.toString();
//    		
//    		String pathToFile = outputFolder + File.separator + "ms" + msnumber + File.separator + 
//    				"src/main/java/" + subpath + File.separator + fileName;
//    		File file = new File(pathToFile);
//    		file.getParentFile().mkdirs();
//    		try
//    		{
//    			PrintWriter pw = new PrintWriter(file);
//    			pw.println(cu.toString());
//    			pw.flush();
//    			pw.close();
//    		}
//    		catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//	    }
//	}
//	
//	private void importGsonPackage(MethodVisitor cv, CompilationUnit cu, String outputFolder)
//	{
//		Set<String> importsToAdd=cv.getImportsRequiredDueToCodeAdditions();
//		for (String importToAdd : importsToAdd)
//		{
//			// TODO: for some reason the CU might already
//			// have these classes imported, so this add will
//			// be redundant (not harmful). Check for presence and
//			// dont add if present already
//			cu.addImport(importToAdd);
//		}
//	
//		// TODO: Adding the Gson to pom.xml programmatically
//		POM.hasDependency("Gson", outputFolder);
//	}
}