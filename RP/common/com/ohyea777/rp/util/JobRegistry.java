package com.ohyea777.rp.util;

import com.ohyea777.rp.jobs.IJob;
import com.ohyea777.rp.jobs.JobCitizen;

public class JobRegistry extends Registry<IJob> {

	@Override
	protected void load() {
		register("Citizen", new JobCitizen());
	}

}
