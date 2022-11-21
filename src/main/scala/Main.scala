object Main {
  @main
  def runSimulations(): Unit ={
    SaaSImplementation.saaSImplementation()
    PaaSImplementation.paaSImplementation()
    IaaSImplementation.iaaSImplementation()
  }

}
