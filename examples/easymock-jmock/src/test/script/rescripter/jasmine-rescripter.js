
function runJasmine() {
	var jasmineEnv = jasmine.getEnv();
	jasmineEnv.updateInterval = 1000;
	var trivialReporter = new RescripterReporter();
	jasmineEnv.addReporter(trivialReporter);
	jasmineEnv.execute();
	while(!trivialReporter.finished) {
	    java.lang.Thread.sleep(1000);
	}
	
	if (trivialReporter.failed) {
		Debug.message("Messages are: "+trivialReporter.messages);
	    Alert.info("Test failed\n"+trivialReporter.messages.join("\n\n"));
	} else {
	    Alert.info("Tests passed");
	}
}	

function RescripterReporter() {
    this.messages = [];
	this.failed = false;
	this.finished = false;
	
    this.reportRunnerStarting = function(runner) {
    };
        
    this.reportRunnerResults = function(runner) {
    };
    
    this.reportSuiteResults =  function(suite) {
        this.finished = true;
    };
    
    this.reportSpecStarting = function(spec) {
    };
    
    this.reportSpecResults = function(spec) {
        this.failed = this.failed || spec.results().failedCount > 0
        if (this.failed) {
        	this.messages.push(spec.suite.description+" "+spec.description+": "+spec.results().getItems());	
        }
    };
    
    this.log = function(msg) {
    };
    
    return this;    
}
