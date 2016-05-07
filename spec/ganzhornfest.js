describe("App", function() {
  beforeEach(module("ngGanzhornfest"));
  var $controller;
  beforeEach(inject(function(_$controller_){
    // The injector unwraps the underscores (_) from around the parameter names when matching
    $controller = _$controller_;
  }));
  it("is true", function() {
    expect(true).toBe(true);
  });
});
