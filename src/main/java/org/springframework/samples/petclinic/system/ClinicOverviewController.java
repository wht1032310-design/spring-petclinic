/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.system;

import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.samples.petclinic.owner.VisitRepository;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/** Displays a live summary of the clinic's registered records. */
@Controller
class ClinicOverviewController {

	private final OwnerRepository ownerRepository;

	private final PetRepository petRepository;

	private final VisitRepository visitRepository;

	private final VetRepository vetRepository;

	ClinicOverviewController(OwnerRepository ownerRepository, PetRepository petRepository,
			VisitRepository visitRepository, VetRepository vetRepository) {
		this.ownerRepository = ownerRepository;
		this.petRepository = petRepository;
		this.visitRepository = visitRepository;
		this.vetRepository = vetRepository;
	}

	@GetMapping("/clinic/overview")
	String show(Model model) {
		model.addAttribute("ownerCount", this.ownerRepository.count());
		model.addAttribute("petCount", this.petRepository.count());
		model.addAttribute("visitCount", this.visitRepository.count());
		model.addAttribute("vetCount", this.vetRepository.count());
		return "clinic/overview";
	}

}
