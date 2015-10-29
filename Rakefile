task :default => [:test]

desc "Test the output"
task :test do
  require 'html/proofer'
  HTML::Proofer.new("./output", { :verbosity => :debug, :cache => { :timeframe => '30d' } }).run
end
