#! /usr/bin/perl -T

use strict;
use warnings qw(FATAL all);

my $length = scalar @ARGV;
my $count = 10;

if ($length == 0) {
	push(@ARGV, "-");
	$length++;
}

if ($ARGV[0] eq "-n") {
	shift(@ARGV);
	$count = shift(@ARGV);
}

foreach my $arg (@ARGV) {
	my $file;
	my @rows = ();

	if ($length > 1) {
		print "==> $arg <==\n"
	}
	
	if ("$arg" eq "-") {
		$file  = *STDIN;
	} else {
		open $file, '<', $arg or die "Failed to open file $arg";
	}

	while (<$file>) {
		if ($. <= $count) {
			push(@rows, "$_");	
		}	
	}	
	
	foreach my $row (@rows) {
		print "$row";
	}	
		
	print "\n";
}
