/*
 * Interface that defines the configuration methods at runtime.   
 * The class implementing this interface will be compiled at startup time.
 */
interface DynamicConfigurationInterface {
	public def getInstanceList()
	public def getRefreshIntervalSecs()
	public def getPipelineSpecs() 
}
