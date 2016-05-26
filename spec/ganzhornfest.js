describe("App", function() {
  var app;
  beforeEach(
    app = module("ngGanzhornfest")
  );

  it("is defined", function() { 
    expect(app).not.toBe(null);
  });

  it("has controllers", function() {
    expect(app.controller).not.toBe(null);
  });
  it("has servies", function() {
    expect(app.services).not.toBe(null);
  });
});
